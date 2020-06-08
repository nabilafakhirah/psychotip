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

public class PsychologistRegistrationPage2InstrumentedTest {

    @Rule
    public IntentsTestRule<PsychologistRegistrationPage2> activityRule
            = new IntentsTestRule<>(PsychologistRegistrationPage2.class);

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
        onView(withId(R.id.continueButton)).check(matches(withText("Lanjut")));
    }

/*    @Test
    public void testContinueIntent() {
        onView(withId(R.id.continueButton)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(PsychologistRegistrationPage3.class.getName()));
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

/*    @Test
    public void buttonShouldCreatePsikolog() {
        onView(withId(R.id.continueButton)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasExtraWithKey("user"));
    }*/


}
