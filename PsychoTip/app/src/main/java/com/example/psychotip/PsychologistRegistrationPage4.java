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

public class PsychologistRegistrationPage4 extends AppCompatActivity {

    private final int requestImageFromGallery = 200;
    private String encodedString;
    private String filename;
    private long timeBeforeUpload;
    private long timeAfterUpload;
    private long fileSize;
    private Button continueButton;
    private Psikolog psikolog;

    private Spinner bankNameSpinner;
    private EditText accountNo;
    private EditText accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologist_registration_page_4);
        createSpinner();

        Intent intent = getIntent();

        psikolog = intent.getParcelableExtra("psikolog");

        if (psikolog == null) {
            psikolog = new Psikolog("username", "email@username.com",
                    "password");
        }

        bankNameSpinner = (Spinner) findViewById(R.id.bankNameSpinner);
        accountNo = (EditText) findViewById(R.id.accountNumber);
        accountName = (EditText) findViewById(R.id.accountName);

        InputFilter[] maxAccountNoLength = new InputFilter[1];
        maxAccountNoLength[0] = new InputFilter.LengthFilter(30);
        accountNo.setFilters(maxAccountNoLength);

        InputFilter[] maxxAccountNameLength = new InputFilter[1];
        maxxAccountNameLength[0] = new InputFilter.LengthFilter(100);
        accountName.setFilters(maxxAccountNameLength);


        continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountNo.getText().toString().isEmpty()
                        || accountName.getText().toString().isEmpty()
                        || bankNameSpinner.getSelectedItem().toString().equalsIgnoreCase(
                                "Nama Bank")) {
                    showErrorMessageIfValueEmpty();
                } else {
                    openTermsandCondition(psikolog, bankNameSpinner, accountNo, accountName);

                }
            }
        });

        final Button fileButton = (Button) findViewById(R.id.accountFileButton);
        fileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromDeviceGallery();
            }
        });
    }

    public void showErrorMessageIfValueEmpty() {
        Snackbar.make(accountName, "Tolong isi seluruh form!", Snackbar.LENGTH_LONG).show();
    }

    public void createSpinner() {
        // Get reference of widgets from XML layout
        final Spinner spinner = (Spinner) findViewById(R.id.bankNameSpinner);

        // Initializing a String Array
        String[] plants = new String[]{"Nama Bank", "BCA", "Mandiri", "BNI", "Lainnya"};

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
    }

    public void openTermsandCondition(Psikolog psikolog, Spinner bankNameSpinner,
                                      EditText accountNo, EditText accountName) {

        String bankNameStr = bankNameSpinner.getSelectedItem().toString();
        psikolog.setNamaBank(bankNameStr);

        String accountNoStr = accountNo.getText().toString();
        psikolog.setNoRekening(accountNoStr);

        String accountNameStr = accountName.getText().toString();
        psikolog.setNamaRekening(accountNameStr);

        Intent intent = new Intent(this, PsychologistTermsAndConditions.class);

        intent.putExtra("psikolog", psikolog);

        startActivity(intent);

    }

}
