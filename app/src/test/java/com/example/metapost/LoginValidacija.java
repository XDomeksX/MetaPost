// app/src/test/java/com/example.metapost.utils/LoginValidationUnitTest.java
package com.example.metapost; // IMPORTANT: This package must match your 'validacija's package in 'test'

import org.junit.Test; // Import the Test annotation
import static org.junit.Assert.assertFalse; // Import static methods for assertions
import static org.junit.Assert.assertTrue;   // Import static methods for assertions

import com.example.metapost.utils.validacija;

/**
 * Unit tests for the 'validacija' class.
 * These tests focus on the pure business logic of credential and field validation,
 * running on the JVM and not requiring an Android device or emulator.
 */
public class LoginValidacija {

    // --- Tests for isValidCredentials method ---

    @Test
    public void validCredentials_returnsTrue() {
        // Test case: Correct username ("admin") and password ("1234") should return true.
        // These values must match the VALID_USERNAME and VALID_PASSWORD in your validacija class.
        assertTrue("Expected valid credentials to return true",
                validacija.isValidCredentials("admin", "1234"));
    }

    @Test
    public void incorrectUsername_returnsFalse() {
        // Test case: Providing an incorrect username should return false, even with a correct password.
        assertFalse("Expected incorrect username to return false",
                validacija.isValidCredentials("wrong_user", "1234"));
    }

    @Test
    public void incorrectPassword_returnsFalse() {
        // Test case: Providing an incorrect password should return false, even with a correct username.
        assertFalse("Expected incorrect password to return false",
                validacija.isValidCredentials("admin", "wrong_pass"));
    }

    @Test
    public void emptyUsername_returnsFalse() {
        // Test case: An empty username string should result in false, regardless of password.
        assertFalse("Expected empty username to return false",
                validacija.isValidCredentials("", "1234"));
    }

    @Test
    public void emptyPassword_returnsFalse() {
        // Test case: An empty password string should result in false, regardless of username.
        assertFalse("Expected empty password to return false",
                validacija.isValidCredentials("admin", ""));
    }

    @Test
    public void nullUsername_returnsFalse() {
        // Test case: A null username should result in false.
        assertFalse("Expected null username to return false",
                validacija.isValidCredentials(null, "1234"));
    }

    @Test
    public void nullPassword_returnsFalse() {
        // Test case: A null password should result in false.
        assertFalse("Expected null password to return false",
                validacija.isValidCredentials("admin", null));
    }

    // --- Tests for isFieldEmpty method ---

    @Test
    public void emptyString_isFieldEmpty_returnsTrue() {
        // Test case: An empty string should be identified as empty.
        assertTrue("Expected empty string to be true", validacija.isFieldEmpty(""));
    }

    @Test
    public void whitespaceString_isFieldEmpty_returnsTrue() {
        // Test case: A string containing only whitespace should be identified as empty.
        assertTrue("Expected whitespace string to be true", validacija.isFieldEmpty("   \t\n"));
    }

    @Test
    public void nullString_isFieldEmpty_returnsTrue() {
        // Test case: A null string should be identified as empty.
        assertTrue("Expected null string to be true", validacija.isFieldEmpty(null));
    }

    @Test
    public void populatedString_isFieldEmpty_returnsFalse() {
        // Test case: A string with actual characters should not be identified as empty.
        assertFalse("Expected populated string to be false", validacija.isFieldEmpty("Hello World"));
    }

    @Test
    public void stringWithLeadingTrailingWhitespace_isFieldEmpty_returnsFalse() {
        // Test case: A string with actual content and surrounding whitespace should not be empty,
        // as .trim() should handle the whitespace.
        assertFalse("Expected string with content and whitespace to be false", validacija.isFieldEmpty("  content  "));
    }
}
