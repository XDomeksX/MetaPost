// app/src/androidTest/java/com/example/metapost/LoginMediumTest.java
package com.example.metapost; // This package MUST match your app's main package

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

// Static imports for Espresso actions, assertions, and matchers
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Instrumentation tests for the Login Screen (MainActivity) of the MetaPost app.
 * These tests run on an Android device or emulator, simulating user interactions
 * and verifying UI behavior and navigation.
 */
@RunWith(AndroidJUnit4.class) // Specifies that this test class should be run with Android's JUnit4 runner
public class LoginMediumTest { // Renamed from LoginScreenInstrumentationTest for your clarification

    /**
     * JUnit Rule that launches a specific Activity (MainActivity in this case)
     * before each test method in this class. This ensures that every test starts
     * with a fresh instance of the Login Screen.
     */
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Test case 1: Verifies that the login button is initially disabled when both
     * username and password fields are empty.
     */
    @Test
    public void loginButton_initiallyDisabled_whenFieldsEmpty() {
        Espresso.onView(withId(R.id.login_button))
                .check(matches(not(isEnabled())));
    }

    /**
     * Test case 2: Verifies that the login button becomes enabled once both
     * username and password fields have some text input.
     */
    @Test
    public void loginButton_enabled_whenFieldsPopulated() {
        Espresso.onView(withId(R.id.username_input))
                .perform(typeText("someuser"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.pass_input))
                .perform(typeText("somepass"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_button))
                .check(matches(isEnabled()));
    }

    /**
     * Test case 3: Verifies that an "Invalid username or password" Snackbar message
     * is displayed when incorrect credentials are entered and the login button is clicked.
     * This test is now reliable because it checks for a Snackbar, not a Toast.
     */
    @Test
    public void incorrectCredentials_showsErrorMessage() {
        // Type incorrect username and password.
        Espresso.onView(withId(R.id.username_input))
                .perform(typeText("wronguser"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.pass_input))
                .perform(typeText("wrongpass"), closeSoftKeyboard());

        // Click the login button.
        Espresso.onView(withId(R.id.login_button)).perform(click());

        // --- THIS IS THE CRUCIAL CHANGE IN THE TEST ---
        // Assert that a View with the text "Invalid username or password" is displayed.
        // This will now correctly find the Snackbar.
        Espresso.onView(withText("Invalid username or password"))
                .check(matches(isDisplayed()));
        // --- END OF CRUCIAL CHANGE ---
    }

    /**
     * Test case 4: Verifies that upon entering correct credentials, the app navigates
     * successfully from MainActivity (Login Screen) to ChattingPageActivity.
     */
    @Test
    public void correctCredentials_navigatesToChattingPage() {
        Espresso.onView(withId(R.id.username_input))
                .perform(typeText("admin"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.pass_input))
                .perform(typeText("1234"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_button)).perform(click());
        Espresso.onView(withId(R.id.chatContent))
                .check(matches(isDisplayed()));
    }
}
