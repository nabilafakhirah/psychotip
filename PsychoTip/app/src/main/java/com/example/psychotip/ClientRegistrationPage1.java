package com.example.psychotip;

import android.content.Intent;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class ClientRegistrationPage1 extends AppCompatActivity {
    private Button lanjutButton;
    EditText username;
    EditText email;
    EditText password;
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_registration_page_1);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        lanjutButton = (Button) findViewById(R.id.continueButton);

        InputFilter[] maxUsernameLength = new InputFilter[1];
        maxUsernameLength[0] = new InputFilter.LengthFilter(15);
        username.setFilters(maxUsernameLength);

        InputFilter[] maxEmailLength = new InputFilter[1];
        maxEmailLength[0] = new InputFilter.LengthFilter(254);
        email.setFilters(maxEmailLength);

        InputFilter[] maxPasswordLength = new InputFilter[1];
        maxPasswordLength[0] = new InputFilter.LengthFilter(20);
        password.setFilters(maxPasswordLength);
        confirmPassword.setFilters(maxPasswordLength);

        lanjutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || email.getText().toString().isEmpty()
                        || password.getText().toString().isEmpty()
                        || confirmPassword.getText().toString().isEmpty()) {
                    showErrorMessageIfValueEmpty();
                } else if (!password.getText().toString().equals(
                        confirmPassword.getText().toString())) {
                    showPasswordDoesntMatchError();
                } else if (password.getText().toString().length() < 8) {
                    showPasswordTooShort();
                } else {
                    openFormDuaDaftarKlien(username, email, password);
                }
            }

        });
    }

    public void showErrorMessageIfValueEmpty() {
        Snackbar.make(confirmPassword, "Tolong isi seluruh form!", Snackbar.LENGTH_LONG).show();
    }

    public void showPasswordDoesntMatchError() {
        Snackbar.make(confirmPassword, "Kata sandi tidak sama!", Snackbar.LENGTH_LONG).show();
    }

    public void showPasswordTooShort() {
        Snackbar.make(confirmPassword, "Kata sandi terlalu pendek. Minimal 8 karakter.",
                Snackbar.LENGTH_LONG).show();
    }


    public void openFormDuaDaftarKlien(EditText username, EditText email, EditText password) {
        Intent intent = new Intent(this, ClientRegistrationPage2.class);

        String usernameStr = username.getText().toString();
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();

        User user = new User(usernameStr, emailStr, passwordStr);

        intent.putExtra("user", user);

        startActivity(intent);
    }
}
