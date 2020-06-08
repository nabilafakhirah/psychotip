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

public class ClientRegistrationPage1Test {

    @Rule
    public IntentsTestRule<ClientRegistrationPage1> activityRule
            = new IntentsTestRule<>(ClientRegistrationPage1.class);

    @Test
    public void testUsernameForm() {
        onView(withId(R.id.username)).check(matches(notNullValue()));
        onView(withId(R.id.username)).check(matches(withHint("Username")));
    }

    @Test
    public void testEmailForm() {
        onView(withId(R.id.email)).check(matches(notNullValue()));
        onView(withId(R.id.email)).check(matches(withHint("Email")));
    }


    @Test
    public void testPasswordForm() {
        onView(withId(R.id.password)).check(matches(notNullValue()));
        onView(withId(R.id.password)).check(matches(withHint("Kata Sandi")));


        onView(withId(R.id.confirmPassword)).check(matches(notNullValue()));
        onView(withId(R.id.confirmPassword)).check(matches(withHint("Konfirmasi Kata Sandi")));
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
        intended(hasComponent(ClientRegistrationPage2.class.getName()));
    }*/

    @Test
    public void testUsernameContent() {
        onView(withId(R.id.username)).perform(typeText("userx"));
        onView(withId(R.id.username)).check(matches(withText("userx")));
    }

    @Test
    public void testEmailContent() {
        onView(withId(R.id.email)).perform(typeText("email@xmail.com"));
        onView(withId(R.id.email)).check(matches(withText("email@xmail.com")));
    }

    @Test
    public void testPasswordContent() {
        onView(withId(R.id.password)).perform(typeText("mypassword"));
        onView(withId(R.id.password)).check(matches(withText("mypassword")));


        onView(withId(R.id.confirmPassword)).perform(typeText("mypassword"));
        onView(withId(R.id.confirmPassword)).check(matches(withText("mypassword")));
    }

/*    @Test
    public void buttonShouldCreateUser() {
        onView(withId(R.id.continueButton)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasExtraWithKey("user"));
    }*/

}
