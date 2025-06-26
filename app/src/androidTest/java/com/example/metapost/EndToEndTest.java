package com.example.metapost;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.metapost.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EndToEndTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void fullUserJourneyTest() throws InterruptedException {
        // --- NAJVAŽNIJA NAPOMENA ---
        // U sljedeće dvije linije unesi ISPRAVAN EMAIL I LOZINKU
        // za jednog od svojih korisnika na Firebaseu!
        String validEmail = "domenikmilohaniceducation@gmail.com";
        String validPassword = "yolo1234";


        // --- NOVI KORACI: PROVJERA REGISTRACIJSKOG EKRANA ---
        // 0. Odlazak na ekran za registraciju
        onView(withId(R.id.register_now_text)).perform(click());
        Thread.sleep(1000);

        // Provjera jesmo li na ekranu za registraciju
        onView(withId(R.id.register_button)).check(matches(isDisplayed()));

        // Povratak na login ekran
        onView(withId(R.id.login_now_text)).perform(click());
        Thread.sleep(1000);

        // Provjera jesmo li se vratili na Login ekran
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        // --- KRAJ NOVIH KORAKA ---


        // 1. Prijava na aplikaciju
        onView(withId(R.id.email_input)).perform(typeText(validEmail), closeSoftKeyboard());
        onView(withId(R.id.pass_input)).perform(typeText(validPassword), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        Thread.sleep(2000);

        // 2. Provjera jesmo li na ekranu s listom razgovora
        onView(withId(R.id.metapod_chat_option)).check(matches(isDisplayed()));

        // 3. Odlazak na Postavke preko donje navigacije
        onView(withId(R.id.nav_settings)).perform(click());
        Thread.sleep(1000);

        // 4. Provjera jesmo li na ekranu s postavkama
        onView(withId(R.id.settings_root_layout)).check(matches(isDisplayed()));

        // 5. Ulazak u 'About Us' ekran
        onView(withId(R.id.about_us_layout)).perform(click());
        Thread.sleep(1000);

        // 6. Provjera jesmo li na 'About Us' ekranu
        onView(withId(R.id.toolbar_about)).check(matches(isDisplayed()));

        // 7. Povratak u Postavke pomoću gumba za natrag
        onView(withId(R.id.about_back_button)).perform(click());
        Thread.sleep(1000);

        // 8. Provjera jesmo li se vratili na ekran s postavkama
        onView(withId(R.id.settings_root_layout)).check(matches(isDisplayed()));

        // 9. Povratak na listu razgovora
        onView(withId(R.id.back_button)).perform(click());
        Thread.sleep(1000);

        // 10. Ulazak u chat
        onView(withId(R.id.metapod_chat_option)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.chat_recycler_view)).check(matches(isDisplayed()));

        // 11. Odjava iz aplikacije preko donje navigacije
        onView(withId(R.id.nav_logout)).perform(click());
        Thread.sleep(1000);

        // 12. Provjera jesmo li se vratili na Login ekran
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }
}