package com.example.metapost;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.metapost.MainActivity; // Ispravljen import

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginScreenTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void incorrectCredentials_showsErrorMessageTextView() {
        // Unosimo nepostojeći email i lozinku
        onView(withId(R.id.email_input))
                .perform(typeText("kriviuser@test.com"), closeSoftKeyboard());
        onView(withId(R.id.pass_input))
                .perform(typeText("krivapass"), closeSoftKeyboard());

        // Kliknemo na gumb za prijavu
        onView(withId(R.id.login_button)).perform(click());

        // !!!!! DODAJEMO PAUZU OVDJE !!!!!
        // Dajemo aplikaciji 2 sekunde da dohvati odgovor od Firebasea
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Provjeravamo je li se naš TextView za grešku pojavio i sadrži li točan tekst
        onView(withId(R.id.error_text))
                .check(matches(isDisplayed()))
                .check(matches(withText("Autentifikacija neuspješna.")));
    }
}