package com.dilshana.api.config;

/** Run settings. Override on the command line, for example -DbaseUrl=https://staging.example.com */
public final class Settings {

    public static final String BASE_URL = System.getProperty("baseUrl", "https://restful-booker.herokuapp.com");
    public static final String USERNAME = System.getProperty("apiUser", "admin");
    public static final String PASSWORD = System.getProperty("apiPassword", "password123");

    private Settings() {
    }
}
