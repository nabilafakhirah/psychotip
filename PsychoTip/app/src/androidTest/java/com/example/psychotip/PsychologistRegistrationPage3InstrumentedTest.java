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

public class PsychologistRegistrationPage3InstrumentedTest {
    @Rule
    public IntentsTestRule<PsychologistRegistrationPage3> activityRule
            = new IntentsTestRule<>(PsychologistRegistrationPage3.class);

    @Test
    public void testSippForm() {
        onView(withId(R.id.sippNumber)).check(matches(notNullValue()));
        onView(withId(R.id.sippNumber)).check(matches(withHint("Nomor SIPP")));
    }

    @Test
    public void testSpecializationForm() {
        onView(withId(R.id.specializationSpinner)).check(matches(notNullValue()));
    }

    @Test
    public void testKtpForm() {
        onView(withId(R.id.ktp)).check(matches(notNullValue()));
        onView(withId(R.id.ktp)).check(matches(withHint("Scan KTP")));
    }

    @Test
    public void testKtpButton() {
        onView(withId(R.id.ktpUploadButton)).check(matches(notNullValue()));
    }

    @Test
    public void testIjazahForm() {
        onView(withId(R.id.ijazah)).check(matches(notNullValue()));
        onView(withId(R.id.ijazah)).check(matches(withHint("Scan Ijazah*")));
    }

    @Test
    public void testIjazahButton() {
        onView(withId(R.id.ijazahUploadButton)).check(matches(notNullValue()));
    }

    @Test
    public void testContinueButton() {
        onView(withId(R.id.continueButton)).check(matches(notNullValue()));
        onView(withId(R.id.continueButton)).check(matches(withText("Lanjut")));
    }

/*    @Test
    public void testContinueIntent() {
        onView(withId(R.id.continueButton)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(PsychologistRegistrationPage4.class.getName()));
    }*/

    @Test
    public void testSippContent() {
        onView(withId(R.id.sippNumber)).perform(typeText("911"));
        onView(withId(R.id.sippNumber)).check(matches(withText("911")));
    }

/*    @Test
    public void buttonShouldCreatePsikolog() {
        onView(withId(R.id.continueButton)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasExtraWithKey("psikolog"));
    }*/



}
