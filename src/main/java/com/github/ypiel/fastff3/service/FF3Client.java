package com.github.ypiel.fastff3.service;

import com.github.ypiel.fastff3.model.Account;
import com.github.ypiel.fastff3.model.Category;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class FF3Client {

    public final static String BASE_API = "http://192.168.0.30/api/v1";

    public final static String KEY;

    static {
        try {

            String ff3Key = System.getProperty("ff3.key.file", System.getenv("FF3_KEY_FILE"));
            KEY = Files.readString(
                    Paths.get(ff3Key)
            ).trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FF3Client() {
    }


    @SneakyThrows
    private String getAPIResult(String enppoint, HttpMethod verb, Map<String, String> headers,
                                Map<String, String> params, Optional<String> body) {


        Map<String, String> allHeaders = new HashMap<>();
        headers.entrySet()
                .forEach(e -> allHeaders.put(e.getKey(), URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8)));
        allHeaders.putIfAbsent("Content-Type", "application/vnd.api+json");
        allHeaders.putIfAbsent("Accept-Encoding", "gzip, deflate");
        allHeaders.putIfAbsent("Accept", "application/vnd.api+json");
        allHeaders.putIfAbsent("Authorization", KEY); //URLEncoder.encode(KEY, StandardCharsets.UTF_8));


        HttpClient client = HttpClient.newHttpClient();
        String queryParams = params.entrySet().stream()
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "=" +
                        URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        URI uri = new URI(String.format("%s/%s?%s", BASE_API, enppoint, queryParams));

        HttpRequest.Builder builder = HttpRequest.newBuilder().uri(uri);
        allHeaders.entrySet().stream().forEach(e -> builder.header(e.getKey(), e.getValue()));

        HttpRequest.BodyPublisher bodyPublisher;
        if (body.isPresent()) {
            bodyPublisher = HttpRequest.BodyPublishers.ofString(body.get());
        } else {
            bodyPublisher = HttpRequest.BodyPublishers.noBody();
        }
        builder.method(verb.name(), bodyPublisher);

        HttpRequest request = builder.build();

        if (log.isDebugEnabled()) {
            String headersStr = headers.entrySet().stream()
                    .map(e -> {
                        String key = e.getKey();
                        String value = e.getValue();
                        if (key.toLowerCase().contains("authorization")) {
                            value = "xxxxxxxxxxx";
                        }
                        return String.format("  - %s: %s", key, value);
                    })
                    .collect(Collectors.joining("\n"));
            log.debug(String.format("""
                    HTTP Query %s/%s
                    * Headers:
                    %s
                    * Body:
                    %s
                    """, verb, uri, headersStr, body.isEmpty() ? "<empty>" : body.get()));
        }

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        if (status / 100 != 2) {
            log.error(String.format("""
                    The HTTP query failed: %s/%s.
                    The returned status is %s.
                    """, verb, uri, status));

            JSONObject error = new JSONObject();
            error.put("status", status);
            error.put("error", response.body());
            return error.toString();
        }

        return response.body();
    }


    public List<Account> getAccountsByType(AccountType type) {
        Map<String, String> params = new HashMap<>();
        params.put("type", type.getLabel());
        String accountsJson = getAPIResult("accounts",
                HttpMethod.GET,
                Collections.emptyMap(),
                params,
                Optional.empty());

        JsonObject response = JsonParser.parseString(accountsJson).getAsJsonObject();
        if (response.has("error")) {
            log.error(accountsJson);
            return Collections.emptyList();
        }

        JsonArray accountsArray = response.getAsJsonArray("data");
        List<Account> accounts = accountsArray.asList().stream()
                .map(e -> e.getAsJsonObject())
                .map(e -> new Account(e.getAsJsonPrimitive("id").getAsString(),
                        e.getAsJsonObject("attributes").getAsJsonPrimitive("name").getAsString()))
                .collect(Collectors.toList());
        return accounts;
    }

    public List<Category> getCategories() {
        String categoriesJson = getAPIResult("autocomplete/categories",
                HttpMethod.GET,
                Collections.emptyMap(),
                Collections.emptyMap(),
                Optional.empty());

        JsonElement jsonElement = JsonParser.parseString(categoriesJson);
        if (!jsonElement.isJsonArray()) {
            log.error(categoriesJson);
            return Collections.emptyList();
        }
        JsonArray categoriesArray = jsonElement.getAsJsonArray();
        List<Category> categories = categoriesArray.asList().stream()
                .map(e -> e.getAsJsonObject())
                .map(e -> new Category(e.getAsJsonPrimitive("id").getAsString(),
                        e.getAsJsonPrimitive("name").getAsString()))
                .collect(Collectors.toList());
        return categories;
    }

    public enum AccountType {

        ALL("all"),
        ASSET("asset"),
        CASH("cash"),
        EXPENSE("expense"),
        REVENUE("revenue"),
        SPECIAL("special"),
        HIDDEN("hidden"),
        LIABILITY("liability"),
        LIABILITIES("liabilities"),
        DEFAULT_ACCOUNT("Default account"),
        CASH_ACCOUNT("Cash account"),
        ASSET_ACCOUNT("Asset account"),
        EXPENSE_ACCOUNT("Expense account"),
        REVENUE_ACCOUNT("Revenue account"),
        INITIAL_BALANCE_ACCOUNT("Initial balance account"),
        BENEFICIARY_ACCOUNT("Beneficiary account"),
        IMPORT_ACCOUNT("Import account"),
        RECONCILIATION_ACCOUNT("Reconciliation account"),
        LOAN("Loan"),
        DEBT("Debt"),
        MORTGAGE("Mortgage");
        private String label;

        AccountType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return this.label;
        }

    }

    public enum HttpMethod {
        GET,
        POST,
        PUT,
        DELETE,
        PATCH,
        HEAD,
        OPTIONS,
        TRACE
    }


}
