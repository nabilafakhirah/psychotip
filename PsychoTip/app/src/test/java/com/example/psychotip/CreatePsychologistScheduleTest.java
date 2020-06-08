package com.example.psychotip;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class CreatePsychologistScheduleTest {
    PsychologistRoutineScheduleFragment schedule = new PsychologistRoutineScheduleFragment();

    @Test
    public void scheduleTimeLessThan90MinutesError(){
        assertEquals(false, schedule.validateTime("10:00", "11:00"));
    }

    @Test
    public void ScheduleTimeMoreThan90Minutes(){
        assertEquals(true, schedule.validateTime("10:00", "11:30"));
    }

    @Test
    public void choseDayFieldIsEmpty(){
        assertEquals(false, schedule.validateField(0, "10:00", "11:30"));
    }

    @Test
    public void choseStartTimeIsEmpty(){
        assertEquals(false, schedule.validateField(1, "", "11:30"));
    }

    @Test
    public void choseEndTimeIsEmpty(){
        assertEquals(false, schedule.validateField(1, "10:00", ""));
    }

    @Test
    public void allFieldsIsEmpty(){
        assertEquals(false, schedule.validateField(0, "", ""));
    }

    @Test
    public void allFieldsIsNotEmpty(){
        assertEquals(true, schedule.validateField(1, "10:00", "11:30"));
    }
}
