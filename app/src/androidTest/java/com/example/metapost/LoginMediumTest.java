package com.example.metapost;

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


@RunWith(AndroidJUnit4.class)
public class LoginMediumTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void loginButton_initiallyDisabled_whenFieldsEmpty() {
        Espresso.onView(withId(R.id.login_button))
                .check(matches(not(isEnabled())));
    }

    @Test
    public void loginButton_enabled_whenFieldsPopulated() {
        Espresso.onView(withId(R.id.username_input))
                .perform(typeText("someuser"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.pass_input))
                .perform(typeText("somepass"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_button))
                .check(matches(isEnabled()));
    }

    @Test
    public void incorrectCredentials_showsErrorMessage() {
        // Type incorrect username and password.
        Espresso.onView(withId(R.id.username_input))
                .perform(typeText("wronguser"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.pass_input))
                .perform(typeText("wrongpass"), closeSoftKeyboard());

        Espresso.onView(withId(R.id.login_button)).perform(click());

        Espresso.onView(withText("Invalid username or password"))
                .check(matches(isDisplayed()));
    }

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
