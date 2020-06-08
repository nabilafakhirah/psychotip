package com.example.psychotip;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)

public class PsychologistEditProfileInstrumentedTest {
    @Rule
    public IntentsTestRule<PsychologistEditProfile> activityRule
            = new IntentsTestRule<>(PsychologistEditProfile.class);

    @Test
    public void testPsychologistName() {
        onView(withId(R.id.name)).check(matches(notNullValue()));
        onView(withId(R.id.name)).check(matches(withText("Sarah Sechan")));
    }

    @Test
    public void testPsychologistUsername() {
        onView(withId(R.id.username)).check(matches(notNullValue()));
        onView(withId(R.id.username)).check(matches(withText("sarah_sechan")));
    }

    @Test
    public void testPsychologistNameChange() {
        onView(withId(R.id.name)).perform(typeText("New Name"));
        onView(withId(R.id.name)).check(matches(withText("New Name")));
    }

    @Test
    public void testPsychologistAddressChange() {
        onView(withId(R.id.address)).perform(typeText("New Address"));
        onView(withId(R.id.address)).check(matches(withText("New Address")));
    }
}
