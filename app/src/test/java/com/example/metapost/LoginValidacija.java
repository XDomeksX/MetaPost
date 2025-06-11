
package com.example.metapost;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.metapost.utils.validacija;

public class LoginValidacija {

    // validCredentials testiranje
    @Test
    public void validCredentials_returnsTrue() {
        assertTrue("Expected valid credentials to return true",
                validacija.isValidCredentials("admin", "1234"));
    }

    @Test
    public void incorrectUsername_returnsFalse() {
        assertFalse("Expected incorrect username to return false",
                validacija.isValidCredentials("wrong_user", "1234"));
    }

    @Test
    public void incorrectPassword_returnsFalse() {
        assertFalse("Expected incorrect password to return false",
                validacija.isValidCredentials("admin", "wrong_pass"));
    }

    @Test
    public void emptyUsername_returnsFalse() {
        assertFalse("Expected empty username to return false",
                validacija.isValidCredentials("", "1234"));
    }

    @Test
    public void emptyPassword_returnsFalse() {
        assertFalse("Expected empty password to return false",
                validacija.isValidCredentials("admin", ""));
    }

    @Test
    public void nullUsername_returnsFalse() {
        assertFalse("Expected null username to return false",
                validacija.isValidCredentials(null, "1234"));
    }

    @Test
    public void nullPassword_returnsFalse() {
        assertFalse("Expected null password to return false",
                validacija.isValidCredentials("admin", null));
    }

    // isFieldEmpty testiranje

    @Test
    public void emptyString_isFieldEmpty_returnsTrue() {
        assertTrue("Expected empty string to be true", validacija.isFieldEmpty(""));
    }

    @Test
    public void whitespaceString_isFieldEmpty_returnsTrue() {
        assertTrue("Expected whitespace string to be true", validacija.isFieldEmpty("   \t\n"));
    }

    @Test
    public void nullString_isFieldEmpty_returnsTrue() {
        assertTrue("Expected null string to be true", validacija.isFieldEmpty(null));
    }

    @Test
    public void populatedString_isFieldEmpty_returnsFalse() {
        assertFalse("Expected populated string to be false", validacija.isFieldEmpty("Hello World"));
    }

    @Test
    public void stringWithLeadingTrailingWhitespace_isFieldEmpty_returnsFalse() {
        assertFalse("Expected string with content and whitespace to be false", validacija.isFieldEmpty("  content  "));
    }
}
