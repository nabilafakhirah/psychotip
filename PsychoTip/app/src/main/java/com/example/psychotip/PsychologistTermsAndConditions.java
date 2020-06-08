package com.example.psychotip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PsychologistTermsAndConditions extends AppCompatActivity {

    private Button continueButton;
    private Psikolog psikolog;
    private CheckBox checkBox;
    private Api registerApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologist_terms_and_conditions);

        Intent intent = getIntent();

        psikolog = intent.getParcelableExtra("psikolog");

        if (psikolog == null) {
            psikolog = new Psikolog("username", "email@username.com",
                    "password");
        }

        checkBox = findViewById(R.id.checkBox);

        continueButton = (Button) findViewById(R.id.daftarButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    registerApi = UtilsApi.getApiService();

                    Map<String, String> fields = new HashMap<>();
                    fields.put("username", psikolog.getUsername());
                    fields.put("email", psikolog.getEmail());
                    fields.put("password", psikolog.getPassword());
                    fields.put("name", psikolog.getName());
                    fields.put("birthday", psikolog.getBirthday());
                    fields.put("gender", psikolog.getGender());
                    fields.put("address", psikolog.getAddress());

                    fields.put("sippNumber", psikolog.getNomorSipp());
                    fields.put("specialization", psikolog.getSpesialisasi());

                    fields.put("bankName", psikolog.getNamaBank());
                    fields.put("acctNumber", psikolog.getNoRekening());
                    fields.put("acctName", psikolog.getNamaRekening());
                    fields.put("role", "psikolog");

                    Call<User> user = registerApi.registerUser(fields);

                    user.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                openPsychologistDashboard();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            call.cancel();
                        }
                    });

                } else {
                    showErrorMessageIfBoxNotChecked();
                }
            }
        });
    }

    public void showErrorMessageIfBoxNotChecked() {
        Snackbar.make(checkBox, "Anda harus mencentang syarat dan kondisi sebelum mendaftar!",
                Snackbar.LENGTH_LONG).show();
    }

    public void openPsychologistDashboard() {
        Intent intent = new Intent(this, PsychologistDashboard.class);
        startActivity(intent);

    }
}
