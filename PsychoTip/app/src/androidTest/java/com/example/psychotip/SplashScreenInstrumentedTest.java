package com.example.psychotip;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)

public class SplashScreenInstrumentedTest {

    @Rule
    public IntentsTestRule<SplashScreen> activityRule
            = new IntentsTestRule<>(SplashScreen.class);

    @Test
    public void testLogo() {
        onView(withId(R.id.imageView2)).check(matches(notNullValue()));
    }

    @Test
    public void testAppName() {
        onView(withId(R.id.textView)).check(matches(notNullValue()));
        onView(withId(R.id.textView)).check(matches(withText("PsychoTip")));
    }


}
