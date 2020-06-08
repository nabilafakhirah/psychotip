package com.example.psychotip;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PsychologistRegistrationPage3 extends AppCompatActivity {
    private final int requestImageFromGallery = 200;
    private String encodedString;
    private String filename;
    private long timeBeforeUpload;
    private long timeAfterUpload;
    private long fileSize;
    private Psikolog psikolog;
    private EditText sippNumber;
    private Spinner spesialisasi;
    private EditText ktp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologist_registration_page_3);

        createSpinner();

        Intent intent = getIntent();
        psikolog = intent.getParcelableExtra("psikolog");
        spesialisasi = (Spinner) findViewById(R.id.specializationSpinner);

        if (psikolog == null) {
            psikolog = new Psikolog("username", "email@username.com",
                    "password");
        }

        sippNumber = (EditText) findViewById(R.id.sippNumber);

        InputFilter[] maxSippLength = new InputFilter[1];
        maxSippLength[0] = new InputFilter.LengthFilter(50);
        sippNumber.setFilters(maxSippLength);


        Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sippNumber.getText().toString().isEmpty()
                        || spesialisasi.getSelectedItem().toString().equalsIgnoreCase(
                                "Bidang Spesialisasi")) {
                    showErrorMessageIfValueEmpty();
                } else {
                    openNextPsychologistForm(psikolog, sippNumber, spesialisasi);
                }
            }
        });

        final Button ktpUploadButton = (Button) findViewById(R.id.ktpUploadButton);
        ktpUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromDeviceGallery();
            }
        });

        final Button ijazahUploadButton = (Button) findViewById(R.id.ijazahUploadButton);
        ijazahUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromDeviceGallery();
            }
        });
    }

    public void showErrorMessageIfValueEmpty() {
        Snackbar.make(sippNumber, "Tolong isi seluruh form!", Snackbar.LENGTH_LONG).show();
    }

    public void createSpinner() {
        // Get reference of widgets from XML layout
        final Spinner spinner = (Spinner) findViewById(R.id.specializationSpinner);

        // Initializing a String Array
        String[] plants = new String[]{"Bidang Spesialisasi", "Psikologi Klinis",
            "Psikologi Pendidikan", "Psikologi Industri dan Organisasi",
            "Psikologi Sosial", "Psikologi Perkembangan"};

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
                    Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    protected void pickImageFromDeviceGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, requestImageFromGallery);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK
                && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

        }

    }

    private void uploadSelectedImageToServer() {
        // make a post request to the server db
        // jadi tuh disini, setiap gambar yg di upload langsung di post ke db? iya gasih?
        // soalnya kalo enggak, nyimpen gambarnya gmn... haha....
    }

    public void openNextPsychologistForm(Psikolog psikolog, EditText sippNumber,
                                         Spinner spesialisasi) {

        String sippNumberStr = sippNumber.getText().toString();
        psikolog.setNomorSipp(sippNumberStr);

        String spesialisasiStr = spesialisasi.getSelectedItem().toString();
        psikolog.setSpesialisasi(spesialisasiStr);

        Intent intent = new Intent(this, PsychologistRegistrationPage4.class);

        intent.putExtra("psikolog", psikolog);

        startActivity(intent);
    }
}
