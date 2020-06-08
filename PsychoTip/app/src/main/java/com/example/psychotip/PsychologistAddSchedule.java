package com.example.psychotip;

import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PsychologistAddSchedule extends AppCompatActivity {

    private EditText timeView;
    private EditText chooseStartTime;
    private EditText chooseEndTime;
    private String timePeriod;

    Intent intent;

    private String chosenDay;
    private String chosenStartTime;
    private String chosenEndTime;

    private int currSchedule;
    private int startMinute;
    private int endMinute;

    private Api createSchedule;

    SessionManager session;
    String loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologist_add_schedule);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView)findViewById(R.id.toolbar_title);
        title.setText("Jadwal Praktek");
        title.setTextColor(getResources().getColor(R.color.colorBlack));

        session = new SessionManager(getApplicationContext());
        loggedInUser = session.getUserDetails().get("username");

        intent = getIntent();
        final String[] dayStr = intent.getStringExtra("date").split(",");


        chooseStartTime = findViewById(R.id.set_routine_start_time);
        chooseStartTime.setInputType(InputType.TYPE_NULL);
        chooseStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeView = chooseStartTime;
                createTime();
            }
        });

        chooseEndTime = findViewById(R.id.set_routine_end_time);
        chooseEndTime.setInputType(InputType.TYPE_NULL);
        chooseEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeView = chooseEndTime;
                createTime();
            }
        });

        Button sendButton = (Button) findViewById(R.id.set_routine_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenDay = dayStr[0];
                chosenStartTime = chooseStartTime.getText().toString();
                chosenEndTime = chooseEndTime.getText().toString();
                openPsychDailySchedule();

                if (!validateField(chosenStartTime, chosenEndTime)) {
                    showErrorMessageIfValueEmpty();
                } else {
                    if (!validateTime(chosenStartTime, chosenEndTime)) {
                        showErrorMessageIfTimeInvalid();
                    } else {
                        Collection<Map<String, String>> maps = new ArrayList<Map<String, String>>();
                        for (int n = 1; n <= currSchedule; n++) {
                            int minute = 90;
                            int currStartMinute = startMinute;
                            int currEndMinute = startMinute + minute;
                            startMinute = currEndMinute;

                            System.out.println(n + " adalah " + currStartMinute
                                    + " sampai " + currEndMinute);

                            chosenStartTime = convertIntegertoTimeString(currStartMinute);
                            chosenEndTime = convertIntegertoTimeString(currEndMinute);

                            Map<String, String> fields = new HashMap<>();
                            fields.put("psikolog", loggedInUser);
                            fields.put("hari", chosenDay);
                            fields.put("jam_kosong_start", chosenStartTime);
                            fields.put("jam_kosong_end", chosenEndTime);

                            System.out.println(fields);

                            maps.add(fields);
                        }
                        createSchedule = UtilsApi.getApiService();
                        for (int i = 0; i < maps.size(); i++) {
                            Call<CreateScheduleResponse> call = createSchedule
                                    .createPsychologistSchedule(
                                            ((ArrayList<Map<String, String>>) maps).get(i));
                            System.out.println("masukkk sini");
                            call.enqueue(new Callback<CreateScheduleResponse>() {
                                @Override
                                public void onResponse(Call<CreateScheduleResponse> call,
                                                       Response<CreateScheduleResponse> response) {
                                    System.out.println("masukkk lagii lagii");
                                    if (response.isSuccessful()) {
                                        //Intent intent = new Intent(PsychologistSetRoutineSchedule.this, PsychologistDashboard.class);
                                        //startActivity(intent);
                                        Toast.makeText(getApplicationContext(), "Jadwal berhasil dibuat", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<CreateScheduleResponse> call,
                                                      Throwable t) {
                                    Log.d("ERROR", "the post process failed");
                                    call.cancel();
                                }
                            });
                        }

                        Intent i = new Intent(getApplicationContext(),
                                PsychologistDashboard.class);
                        startActivity(i);

                        /*Fragment fragment = new PsychologistHomeFragment();
                        replaceFragment(fragment);*/
                    }
                }
            }
        });

    }


    public void createTime() {
        /*Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(PsychologistAddSchedule.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                if (hourOfDay >= 12) {
                    timePeriod = " PM";
                } else {
                    timePeriod = " AM";
                }
                timeView.setText(String.format("%02d:%02d", hourOfDay, minutes) + timePeriod);
            }
        }, currentHour, currentMinute, false);

        timePickerDialog.show();*/
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(PsychologistAddSchedule.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        timeView.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, currentHour, currentMinute, false);

        timePickerDialog.show();
    }

    public void openPsychDailySchedule() {
        Intent intent = new Intent(this, PsychologistDailyScheduleActivity.class);
        startActivity(intent);
    }


    /*public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_psychologist, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/

    public boolean validateTime(String startTime, String endTime) {

        int[] start = convertTimeStringtoInteger(startTime);
        int[] end = convertTimeStringtoInteger(endTime);

        startMinute = convertTimeIntegertoMinute(start);
        endMinute = convertTimeIntegertoMinute(end);
        int duration = endMinute - startMinute;

        if (duration < 90) {
            return false;
        } else {
            currSchedule = duration / 90;
            return true;
        }
    }

    public int convertTimeIntegertoMinute(int[] time) {
        int minute = time[0] * 60 + time[1];
        return minute;
    }

    public boolean validateField(String chosenStartTime, String chosenEndTime) {
        if (chosenStartTime.isEmpty() || chosenEndTime.isEmpty()) {
            return false;
        }
        return true;
    }

    public int[] convertTimeStringtoInteger(String time) {
        String[] timeNumberSplit = time.split(":");

        int hour = Integer.parseInt(timeNumberSplit[0]);
        int minute = Integer.parseInt(timeNumberSplit[1]);

        int[] timeArray = {hour, minute};

        return timeArray;
    }

    public String convertIntegertoTimeString(int minute) {
        int hourTime = minute / 60;
        int minuteTime = minute % 60;
        if (minuteTime == 0) {
            return hourTime + ":00";
        }
        return hourTime + ":" + minuteTime;
    }
    public void showErrorMessageIfValueEmpty() {
        Toast.makeText(getApplicationContext(), "Tolong isi seluruh form!",
                Toast.LENGTH_SHORT).show();
    }

    public void showErrorMessageIfTimeInvalid() {
        Toast.makeText(getApplicationContext(), "Rentang waktu kurang dari 90 menit!",
                Toast.LENGTH_SHORT).show();
    }
}
