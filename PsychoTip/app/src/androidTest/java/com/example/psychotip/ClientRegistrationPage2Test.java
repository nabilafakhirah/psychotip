package com.example.psychotip;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
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

public class ClientRegistrationPage2Test {

    @Rule
    public IntentsTestRule<ClientRegistrationPage2> activityRule
            = new IntentsTestRule<>(ClientRegistrationPage2.class);

    @Test
    public void testNameForm() {
        onView(withId(R.id.name)).check(matches(notNullValue()));
        onView(withId(R.id.name)).check(matches(withHint("Nama")));
    }

    @Test
    public void testBirthdateForm() {
        onView(withId(R.id.birthdate)).check(matches(notNullValue()));
        onView(withId(R.id.birthdate)).check(matches(withHint("Tanggal Lahir")));
    }

    @Test
    public void testGenderForm() {
        onView(withId(R.id.gender)).check(matches(notNullValue()));
    }

    @Test
    public void testAddressForm() {
        onView(withId(R.id.address)).check(matches(notNullValue()));
        onView(withId(R.id.address)).check(matches(withHint("Alamat")));
    }

    @Test
    public void testContinueButton() {
        onView(withId(R.id.continueButton)).check(matches(notNullValue()));
        onView(withId(R.id.continueButton)).check(matches(withText("LANJUT")));
    }

/*    @Test
    public void testContinueIntent() {
        onView(withId(R.id.continueButton)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(ClientTermsAndConditions.class.getName()));
    }*/

    @Test
    public void testNameContent() {
        onView(withId(R.id.name)).perform(typeText("My Name"));
        onView(withId(R.id.name)).check(matches(withText("My Name")));
    }

    @Test
    public void testAddressContent() {
        onView(withId(R.id.address)).perform(typeText("Jl. Rumah"));
        onView(withId(R.id.address)).check(matches(withText("Jl. Rumah")));
    }
/*
    @Test
    public void buttonShouldCreateUser() {
        onView(withId(R.id.continueButton)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasExtraWithKey("user"));
    }*/

}
