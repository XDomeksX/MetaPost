// app/src/androidTest/java/com/example/metapost/FullAppFlowE2ETest.java
package com.example.metapost;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;

/**
 * End-to-End test for a complete user flow through the MetaPost app.
 * This test simulates logging in, navigating to the chat page, then to settings,
 * returning to chat, and finally logging out.
 *
 * This test runs on an Android device or emulator.
 */
@RunWith(AndroidJUnit4.class)
public class EndToEndTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void fullUserJourney_successful() {
        // --- 1. Perform Successful Login to reach Chatting Page ---
        Espresso.onView(withId(R.id.username_input))
                .perform(typeText("admin"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.pass_input))
                .perform(typeText("1234"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_button))
                .perform(click());

        // Introduce a small delay to allow ChattingPageActivity to fully load and render its UI.
        // 1000 milliseconds = 1 second. Adjust if needed (e.g., 2000 for 2 seconds).
        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify navigation to Chatting Page by checking for its main content layout ID.
        Espresso.onView(withId(R.id.chatContent))
                .check(matches(isDisplayed()));


        // --- 2. Navigate from Chatting Page to Settings Screen ---
        // Tap the "Settings" item in the BottomNavigationView.
        Espresso.onView(withId(R.id.nav_settings)) // Using R.id.nav_settings from bottom_nav_menu.xml
                .perform(click());

        // Introduce a small delay to allow SettingsActivity to fully load and render its UI.
        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify navigation to Settings Screen by checking for its root layout ID.
        Espresso.onView(withId(R.id.settings_root_layout)) // Using the ID you added to settings.xml
                .check(matches(isDisplayed()));


        // --- 3. Navigate from Settings Screen back to Chatting Page ---
        // Tap the custom back button ImageView in the Settings screen's top bar.
        Espresso.onView(withId(R.id.back_button)) // Using R.id.back_button from settings.xml
                .perform(click());

        // Introduce a small delay to allow ChattingPageActivity to fully load and render its UI.
        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify navigation back to Chatting Page by checking for its main content layout ID.
        Espresso.onView(withId(R.id.chatContent))
                .check(matches(isDisplayed()));


        // --- 4. Navigate from Chatting Page to Logout (back to Login Screen) ---
        // Tap the "Logout" item in the BottomNavigationView.
        Espresso.onView(withId(R.id.nav_logout)) // Using R.id.nav_logout from bottom_nav_menu.xml
                .perform(click());

        // Introduce a small delay to allow MainActivity to fully load and render its UI after logout.
        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify navigation back to Login Screen by checking for the username input field ID.
        Espresso.onView(withId(R.id.username_input))
                .check(matches(isDisplayed()));
    }
}
