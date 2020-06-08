package com.example.psychotip;

import com.example.psychotip.model.LoginPage;

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

public class LandingPageInstrumentedTest {

    @Rule
    public IntentsTestRule<LandingPage> activityRule
            = new IntentsTestRule<>(LandingPage.class);


    @Test
    public void testRegisterButton() {
        onView(withId(R.id.daftar_button)).check(matches(notNullValue()));
        onView(withId(R.id.daftar_button)).check(matches(withText("DAFTAR")));
    }

    @Test
    public void testRegisterIntent(){
        onView(withId(R.id.daftar_button)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(RegistrationList.class.getName()));

    }

    @Test
    public void testLoginButton() {
        onView(withId(R.id.masuk_button)).check(matches(notNullValue()));
        onView(withId(R.id.masuk_button)).check(matches(withText("MASUK")));
    }

    @Test
    public void testLoginIntent(){
        onView(withId(R.id.masuk_button)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(LoginPage.class.getName()));

    }

}