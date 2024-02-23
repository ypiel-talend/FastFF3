package com.github.ypiel.fastff3.service;

import com.github.ypiel.fastff3.model.Account;
import com.github.ypiel.fastff3.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UIService {

    private final FF3Client ff3Client;

    private List<Account> assetAccounts = new ArrayList<>();
    private List<Account> expenseAccounts = new ArrayList<>();
    private List<Account> revenueAccounts = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    @Autowired
    public UIService(FF3Client ff3Client){
        this.ff3Client = ff3Client;
    }

    public List<Account> getAssetAccounts(){
        if(assetAccounts == null || assetAccounts.isEmpty()) {
            assetAccounts = List.copyOf(ff3Client.getAccountsByType(FF3Client.AccountType.ASSET_ACCOUNT));
        }
        return this.assetAccounts;
    }

    public List<Account> getExpenseAccounts(){
        if(expenseAccounts == null || expenseAccounts.isEmpty()) {
            expenseAccounts = List.copyOf(ff3Client.getAccountsByType(FF3Client.AccountType.EXPENSE_ACCOUNT));
        }
        return this.expenseAccounts;
    }

    public List<Account> getRevenueAccounts(){
        if(revenueAccounts == null || revenueAccounts.isEmpty()) {
            revenueAccounts = List.copyOf(ff3Client.getAccountsByType(FF3Client.AccountType.REVENUE_ACCOUNT));
        }
        return this.revenueAccounts;
    }

    public List<Category> getCategories(){
        if(categories == null || categories.isEmpty()) {
            categories = List.copyOf(ff3Client.getCategories());
        }
        return this.categories;
    }

    public List<String> getTags(){
        return this.tags;
    }


}
