package com.example.metapost;

import android.view.WindowManager;
import androidx.test.espresso.Root;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ToastMatcher extends TypeSafeMatcher<Root> {
    @Override
    public void describeTo(Description description) {
        description.appendText("is toast");
    }

    @Override
    public boolean matchesSafely(Root root) {
        // Provjeravamo je li 'tip' prozora jednak onome za Toast poruke
        int type = root.getWindowLayoutParams().get().type;
        return type == WindowManager.LayoutParams.TYPE_TOAST;
    }
}