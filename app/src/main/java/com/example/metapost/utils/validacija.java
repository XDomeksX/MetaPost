// app/src/main/java/com/example.metapost.utils/validacija.java
package com.example.metapost.utils; // IMPORTANT: This package must match the path you created

public class validacija {

    // --- Important: Replace these with your actual valid credentials ---
    // These credentials must match what your MainActivity expects for a successful login.
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "1234";
    // ------------------------------------------------------------------

    /**
     * Checks if the provided username and password are valid against predefined credentials.
     * This method contains the core business logic for login validation.
     *
     * @param username The username string entered by the user.
     * @param password The password string entered by the user.
     * @return true if both username and password match the valid credentials, false otherwise.
     */
    public static boolean isValidCredentials(String username, String password) {
        // Simple comparison for demonstration. In a real app, this would involve
        // more complex logic (e.g., hashing passwords, checking against a database).
        return VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password);
    }

    /**
     * Checks if a given text string is empty or contains only whitespace.
     * This utility method helps in validating input fields.
     *
     * @param text The string to check for emptiness. Can be null.
     * @return true if the string is null, empty, or consists only of whitespace characters,
     * false otherwise.
     */
    public static boolean isFieldEmpty(String text) {
        // Trim removes leading and trailing whitespace before checking if it's empty.
        // Also handles null strings gracefully.
        return text == null || text.trim().isEmpty();
    }
}
