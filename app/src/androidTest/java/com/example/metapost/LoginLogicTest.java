package com.example.metapost;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class LoginLogicTest {

    @Test
    public void areCredentialsValid_CorrectInput_ReturnsTrue() {
        // Pokrećemo Activity samo da bismo dobili njegovu instancu
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            // Sada na pravoj instanci pozivamo metodu i provjeravamo rezultat
            assertTrue(activity.areCredentialsValid("admin", "1234"));
        });
    }

    @Test
    public void areCredentialsValid_WrongUsername_ReturnsFalse() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            assertFalse(activity.areCredentialsValid("krivi_user", "1234"));
        });
    }

    @Test
    public void areCredentialsValid_WrongPassword_ReturnsFalse() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            assertFalse(activity.areCredentialsValid("admin", "kriva_lozinka"));
        });
    }
}