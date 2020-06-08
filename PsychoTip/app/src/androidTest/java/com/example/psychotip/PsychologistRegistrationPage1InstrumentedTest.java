package com.example.psychotip;

import android.content.Intent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)

public class PsychologistRegistrationPage1InstrumentedTest {

    @Rule
    public IntentsTestRule<PsychologistRegistrationPage1> activityRule
            = new IntentsTestRule<>(PsychologistRegistrationPage1.class);

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
        intended(hasComponent(PsychologistRegistrationPage2.class.getName()));
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
    public void buttonShouldCreatePsikolog() {
        onView(withId(R.id.continueButton)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasExtraWithKey("psikolog"));
    }*/

}
