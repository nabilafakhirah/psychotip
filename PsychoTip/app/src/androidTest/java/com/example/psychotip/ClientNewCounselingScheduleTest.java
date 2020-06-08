package com.example.psychotip;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)

public class ClientNewCounselingScheduleTest {

    @Rule
    public IntentsTestRule<ClientNewCounselingSchedule> activityRule
            = new IntentsTestRule<>(ClientNewCounselingSchedule.class);

    @Test
    public void testWavesAsset() {
        onView(withId(R.id.waves_asset)).check(matches(notNullValue()));
    }

    @Test
    public void testProblemTitle() {
        onView(withId(R.id.problem_title)).check(matches(notNullValue()));
    }

    @Test
    public void testChooseTime() {
        onView(withId(R.id.etChooseTime)).check(matches(notNullValue()));
    }

    @Test
    public void testCounselingDate() {
        onView(withId(R.id.counseling_date)).check(matches(notNullValue()));
    }

    @Test
    public void testCounselingDuration() {
        onView(withId(R.id.counseling_duration)).check(matches(notNullValue()));
    }

    @Test
    public void testProblemDescription() {
        onView(withId(R.id.problem_description)).check(matches(notNullValue()));
    }

    @Test
    public void testContinueButton() {
        onView(withId(R.id.continueButton)).check(matches(notNullValue()));
    }

    @Test
    public void testProblemTitleContent() {
        onView(withId(R.id.problem_title)).perform(typeText("Lagi Banyak Masalah"));
        onView(withId(R.id.problem_title)).check(matches(withText("Lagi Banyak Masalah")));
    }

    @Test
    public void testProblemDescriptionContent() {
        onView(withId(R.id.problem_description)).perform(typeText("Banyak masalah, eottokhae?"));
        onView(withId(R.id.problem_description)).check(matches(withText("Banyak masalah, eottokhae?")));
    }
}
