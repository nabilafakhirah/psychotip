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

public class ClientDashboardInstrumentedTest {
    @Rule
    public IntentsTestRule<ClientDashboard> activityRule
            = new IntentsTestRule<>(ClientDashboard.class);

    @Test
    public void testNameText() {
        onView(withId(R.id.client_name)).check(matches(notNullValue()));
    }

    @Test
    public void testQuoteText() {
        onView(withId(R.id.quote)).check(matches(notNullValue()));
    }

    @Test
    public void testQuoteAuthor() {
        onView(withId(R.id.author)).check(matches(notNullValue()));
    }

    @Test
    public void testLogoutButton() {
        onView(withId(R.id.logoutButton)).check(matches(notNullValue()));
        onView(withId(R.id.logoutButton)).check(matches(withText("LOGOUT")));
    }

    @Test
    public void testLogoutIntent(){
        onView(withId(R.id.logoutButton)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(LandingPage.class.getName()));
    }
}
