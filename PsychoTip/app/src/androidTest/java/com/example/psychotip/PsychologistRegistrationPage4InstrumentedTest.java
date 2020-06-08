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
public class PsychologistRegistrationPage4InstrumentedTest {

    @Rule
    public IntentsTestRule<PsychologistRegistrationPage4> activityRule
            = new IntentsTestRule<>(PsychologistRegistrationPage4.class);

    @Test
    public void testBankNameForm() {
        onView(withId(R.id.bankNameSpinner)).check(matches(notNullValue()));
    }

    @Test
    public void testAccountNumberForm() {
        onView(withId(R.id.accountNumber)).check(matches(notNullValue()));
        onView(withId(R.id.accountNumber)).check(matches(withHint("Nomor Rekening")));
    }

    @Test
    public void testAccountNameForm() {
        onView(withId(R.id.accountName)).check(matches(notNullValue()));
        onView(withId(R.id.accountName)).check(matches(withHint("Nama Tertera pada Rekening")));
    }

    @Test
    public void testAccountBookForm() {
        onView(withId(R.id.accountFile)).check(matches(notNullValue()));
        onView(withId(R.id.accountFile)).check(matches(withHint("Hal. Depan Buku Rekening")));
    }

    @Test
    public void testAccountBookButton() {
        onView(withId(R.id.accountFileButton)).check(matches(notNullValue()));
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
        intended(hasComponent(PsychologistTermsAndConditions.class.getName()));
    }*/

    @Test
    public void testAccountNumberContent() {
        onView(withId(R.id.accountNumber)).perform(typeText("911"));
        onView(withId(R.id.accountNumber)).check(matches(withText("911")));
    }

    @Test
    public void testAccountNameContent() {
        onView(withId(R.id.accountName)).perform(typeText("Sereh"));
        onView(withId(R.id.accountName)).check(matches(withText("Sereh")));
    }

/*    @Test
    public void buttonShouldCreatePsikolog() {
        onView(withId(R.id.continueButton)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasExtraWithKey("psikolog"));
    }*/

}
