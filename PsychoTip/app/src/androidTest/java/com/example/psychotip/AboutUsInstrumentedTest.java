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

public class AboutUsInstrumentedTest {

    @Rule
    public IntentsTestRule<AboutUs> activityRule
            = new IntentsTestRule<>(AboutUs.class);


    @Test
    public void testVisionTitleText() {
        onView(withId(R.id.visionTitle)).check(matches(notNullValue()));
        onView(withId(R.id.visionTitle)).check(matches(withText("Our Vision")));
    }

    @Test
    public void testVisionContentText() {
        onView(withId(R.id.visionContent)).check(matches(notNullValue()));
    }

    @Test
    public void testMissionTitleText() {
        onView(withId(R.id.missionTitle)).check(matches(notNullValue()));
        onView(withId(R.id.missionTitle)).check(matches(withText("Our Mission")));
    }

    @Test
    public void testMissionContentText() {
        onView(withId(R.id.missionContent)).check(matches(notNullValue()));
    }

    @Test
    public void testTeamTitleText() {
        onView(withId(R.id.membersTitle)).check(matches(notNullValue()));
        onView(withId(R.id.membersTitle)).check(matches(withText("Our Team")));
    }

    @Test
    public void testTeamContentText() {
        onView(withId(R.id.gridLayout)).check(matches(notNullValue()));
        onView(withId(R.id.member1)).check(matches(notNullValue()));
        onView(withId(R.id.member2)).check(matches(notNullValue()));
    }

    @Test
    public void testContactUsButton() {
        onView(withId(R.id.contactButton)).check(matches(notNullValue()));
        onView(withId(R.id.contactButton)).check(matches(withText("Hubungi Kami")));
    }

    @Test
    public void testContactUsIntentSuccess(){
        onView(withId(R.id.contactButton)).perform(scrollTo(), click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(ContactUsActivity.class.getName()));
    }

}
