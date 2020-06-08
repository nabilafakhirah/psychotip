package com.example.psychotip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ClientRegistrationPage2 extends AppCompatActivity {
    private static final String TAG = "ClientRegistrationPage2";

    DatePickerDialog picker;
    EditText editText;
    Button continueButton;
    private User user;
    private EditText nama;
    private EditText address;
    private TextView birthdate;
    private Spinner gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_client_registration_page_2);
        user = intent.getParcelableExtra("user");

        if (user == null) {
            user = new User("testusername", "testemail@mail.com",
                    "testpassword");
        }

        nama = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        birthdate = (TextView) findViewById(R.id.birthdate);
        gender = (Spinner) findViewById(R.id.gender);

        InputFilter[] maxNameLength = new InputFilter[1];
        maxNameLength[0] = new InputFilter.LengthFilter(25);
        nama.setFilters(maxNameLength);

        InputFilter[] maxAddressLength = new InputFilter[1];
        maxAddressLength[0] = new InputFilter.LengthFilter(200);
        address.setFilters(maxAddressLength);


        continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nama.getText().toString().isEmpty() || address.getText().toString().isEmpty()
                        || birthdate.getText().toString().isEmpty()
                        || gender.getSelectedItem().toString().equalsIgnoreCase("Jenis Kelamin")) {
                    showErrorMessageIfValueEmpty();
                } else {
                    openTermsAndCondition(user, nama, birthdate, gender, address);
                }
            }
        });

        createCalendar();
        createSpinner();
    }

    public void showErrorMessageIfValueEmpty() {
        Snackbar.make(nama, "Tolong isi seluruh form!", Snackbar.LENGTH_LONG).show();
    }

    public void createCalendar() {
        editText = (EditText) findViewById(R.id.birthdate);
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
                picker = new DatePickerDialog(ClientRegistrationPage2.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

    }

    public void createSpinner() {
        // Get reference of widgets from XML layout
        final Spinner spinner = (Spinner) findViewById(R.id.gender);

        // Initializing a String Array
        String[] plants = new String[]{"Jenis Kelamin", "Laki-Laki", "Perempuan", "Lainnya"};

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
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                    Toast.makeText(
                            getApplicationContext(), "Selected : " + selectedItemText,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void openTermsAndCondition(User user, EditText nama, TextView birthday, Spinner gender,
                                      EditText address) {
        String namaStr = nama.getText().toString();
        user.setName(namaStr);

        String birthdayStr = birthday.getText().toString();
        user.setBirthday(birthdayStr);

        String genderStr = gender.getSelectedItem().toString();
        user.setGender(genderStr);

        String addressStr = address.getText().toString();
        user.setAddress(addressStr);

        Intent intent = new Intent(this, ClientTermsAndConditions.class);

        intent.putExtra("user", user);
        startActivity(intent);


    }
}
