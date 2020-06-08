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

public class PsychologistProfileInstrumentedTest {
    @Rule
    public IntentsTestRule<PsychologistProfile> activityRule
            = new IntentsTestRule<>(PsychologistProfile.class);

    @Test
    public void testClientName() {
        onView(withId(R.id.name)).check(matches(notNullValue()));
        onView(withId(R.id.name)).check(matches(withText("Sarah Sechan")));
    }

    @Test
    public void testLogoutButton() {
        onView(withId(R.id.logoutButton)).check(matches(notNullValue()));
        onView(withId(R.id.logoutButton)).check(matches(withText("KELUAR")));
    }

    @Test
    public void testLogoutIntent(){
        onView(withId(R.id.logoutButton)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(LandingPage.class.getName()));
    }
}
