package com.example.metapost.utils;

public class validacija {

    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "1234";
    // ------------------------------------------------------------------

    public static boolean isValidCredentials(String username, String password) {
        return VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password);
    }

    public static boolean isFieldEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}
