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
@LargeTest

public class RegistrationListInstrumentedTest {

    @Rule
    public IntentsTestRule<RegistrationList> activityRule
            = new IntentsTestRule<>(RegistrationList.class);


    @Test
    public void testClientButton() {
        onView(withId(R.id.client_button)).check(matches(notNullValue()));
        onView(withId(R.id.client_button)).check(matches(withText("KLIEN")));
    }

    @Test
    public void testClientIntent(){
        onView(withId(R.id.client_button)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(ClientRegistrationPage1.class.getName()));

    }

    @Test
    public void testPsychologistButton() {
        onView(withId(R.id.psychologist_button)).check(matches(notNullValue()));
        onView(withId(R.id.psychologist_button)).check(matches(withText("PSIKOLOG")));
    }

    @Test
    public void testPsychologistIntent(){
        onView(withId(R.id.psychologist_button)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(PsychologistRegistrationPage1.class.getName()));

    }
}
