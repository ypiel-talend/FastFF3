package com.github.ypiel.fastff3.ff3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FF3Client {

    public final static String BASE_API = "";

    public final static String KEY;

    static {
        try {
            KEY = Files.readString(
                    Paths.get(System.getProperty("ff3.key.file"))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
