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

public class ClientTermsAndConditionsTest {

    @Rule
    public IntentsTestRule<ClientTermsAndConditions> activityRule
            = new IntentsTestRule<>(ClientTermsAndConditions.class);

    @Test
    public void testContent() {
        onView(withId(R.id.textView6)).check(matches(notNullValue()));
        onView(withId(R.id.scrollView2)).check(matches(notNullValue()));
    }

/*    @Test
    public void testDaftarButtonIntent() {
        onView(withId(R.id.daftarButton)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(ClientDashboard.class.getName()));

    }*/
}
