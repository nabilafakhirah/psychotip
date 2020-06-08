package com.example.psychotip;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class PsychologistAddScheduleInstrumentedTest {
    @Rule
    public IntentsTestRule<PsychologistAddSchedule> activityRule
            = new IntentsTestRule<>(PsychologistAddSchedule.class);

}
