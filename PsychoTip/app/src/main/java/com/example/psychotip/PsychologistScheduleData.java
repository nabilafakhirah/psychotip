package com.example.psychotip;

public class PsychologistScheduleData {
    private String scheduleStatus;
    private String scheduleTime;

    public PsychologistScheduleData(String text1, String text2) {
        scheduleStatus = text1;
        scheduleTime = text2;
    }

    public void changeScheduleStatus(String text) {
        scheduleStatus = text;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }
}