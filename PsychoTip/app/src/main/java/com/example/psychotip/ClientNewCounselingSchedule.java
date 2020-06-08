package com.example.psychotip;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientNewCounselingSchedule extends AppCompatActivity {
    private static final String TAG = "ClientNewCounselingSchedule";

    DatePickerDialog picker;
    EditText editText;
    Button continueButton;
    SessionManager session;

    private EditText problemTitle;
    private EditText problemDesc;
    private TextView counsDate;
    private Spinner duration;
    private EditText chooseTime;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private String category;
    private int currentHour;
    private int currentMinute;
    private String amPm;

    private Api counselingApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        setContentView(R.layout.activity_client_new_counseling_schedule);

        problemTitle = (EditText) findViewById(R.id.problem_title);
        problemDesc = (EditText) findViewById(R.id.problem_description);
        counsDate = (TextView) findViewById(R.id.counseling_date);
        duration = (Spinner) findViewById(R.id.counseling_duration);

        chooseTime = findViewById(R.id.etChooseTime);
        chooseTime.setInputType(InputType.TYPE_NULL);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(ClientNewCounselingSchedule.this,
                        new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                            if (hourOfDay >= 12) {
                                amPm = " PM";
                            } else {
                                amPm = " AM";
                            }
                            chooseTime.setText(
                                    String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                        }
                    }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });

        continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (problemTitle.getText().toString().isEmpty()
                        || problemDesc.getText().toString().isEmpty()
                        || counsDate.getText().toString().isEmpty()
                        || duration.getSelectedItem().toString().equalsIgnoreCase("Durasi")) {
                    showErrorMessageIfValueEmpty();
                } else {
                    sendScheduleToDb(problemTitle, problemDesc, counsDate, chooseTime, duration);
                }
            }
        });

        createCalendar();
        createSpinner();
    }

    public void showErrorMessageIfValueEmpty() {
        Snackbar.make(problemTitle, "Tolong isi seluruh form!", Snackbar.LENGTH_LONG).show();
    }

    public void createCalendar() {
        editText = (EditText) findViewById(R.id.counseling_date);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ClientNewCounselingSchedule.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

    }

    public void createSpinner() {
        // Get reference of widgets from XML layout
        final Spinner spinner = (Spinner) findViewById(R.id.counseling_duration);

        // Initializing a String Array
        String[] plants = new String[]{
                "Durasi",
                "90 menit",
                "180 menit"
        };

        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, plantsList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int black = getResources().getColor(R.color.colorBlack);
                String selectedItemText = (String) parent.getItemAtPosition(position);
                ((TextView) parent.getChildAt(0)).setTextColor(black);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void sendScheduleToDb(EditText problemTitle, EditText problemDesc, TextView counsDate,
                                 TextView chooseTime, Spinner duration) {
       // disini ngirim data konseling ke database
        counselingApi = UtilsApi.getApiService();
        session = new SessionManager(this.getApplicationContext());
        String time[] = chooseTime.getText().toString().split(" ");

        Map<String, String> fields = new HashMap<>();
        fields.put("id_konsul", counsDate.getText().toString());
        fields.put("klien", session.getUserDetails().get("username"));
        fields.put("spesialisasi", category);
        fields.put("tanggal", counsDate.getText().toString());
        fields.put("waktu", time[0]);
        fields.put("durasi", duration.getSelectedItem().toString());

        Call<GetCreateCounselingScheduleResponse> schedule = counselingApi.createSchedule(fields);

        schedule.enqueue(new Callback<GetCreateCounselingScheduleResponse>() {
            @Override
            public void onResponse(Call<GetCreateCounselingScheduleResponse> call, Response<GetCreateCounselingScheduleResponse> response) {
                if(response.isSuccessful()) {
                    openChatList();
                }
            }

            @Override
            public void onFailure(Call<GetCreateCounselingScheduleResponse> call, Throwable t) {
                Log.d("ERROR", "the post process failed");
                call.cancel();
            }
        });

        openChatList();

    }

    public void openChatList() {
        Intent intent = new Intent(this, ClientDashboard.class);
        startActivity(intent);

    }
}
