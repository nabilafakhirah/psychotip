package com.example.psychotip;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)

public class ClientEditProfileInstrumentedTest {
    @Rule
    public IntentsTestRule<ClientEditProfile> activityRule
            = new IntentsTestRule<>(ClientEditProfile.class);

    @Test
    public void testClientName() {
        onView(withId(R.id.name)).check(matches(notNullValue()));
        onView(withId(R.id.name)).check(matches(withText("Jane Doe")));
    }

    @Test
    public void testClientUsername() {
        onView(withId(R.id.username)).check(matches(notNullValue()));
        onView(withId(R.id.username)).check(matches(withText("jane_doe")));
    }

    @Test
    public void testClientNameChange() {
        onView(withId(R.id.name)).perform(typeText("New Name"));
        onView(withId(R.id.name)).check(matches(withText("New Name")));
    }

    @Test
    public void testClientAddressChange() {
        onView(withId(R.id.address)).perform(typeText("New Address"));
        onView(withId(R.id.address)).check(matches(withText("New Address")));
    }
}
