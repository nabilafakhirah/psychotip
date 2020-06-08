package com.example.psychotip;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(AndroidJUnit4.class)
@LargeTest

public class PsychologistTermsAndConditionsTest {

    @Rule
    public IntentsTestRule<PsychologistTermsAndConditions> activityRule
            = new IntentsTestRule<>(PsychologistTermsAndConditions.class);

    @Test
    public void testContent() {
        onView(withId(R.id.textView6)).check(matches(notNullValue()));
        onView(withId(R.id.scrollView2)).check(matches(notNullValue()));
    }

/*    @Test
    public void testDaftarButtonIntent() {
        onView(withId(R.id.daftarButton)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(PsychologistHomeFragment.class.getName()));

    }*/

}
