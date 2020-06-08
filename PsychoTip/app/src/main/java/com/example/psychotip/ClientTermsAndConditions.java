package com.example.psychotip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class ClientTermsAndConditions extends AppCompatActivity {

    private Button continueButton;
    private CheckBox checkBox;
    private Api registerApi;

    public static final String MyPREFERENCES = "MyPrefs";

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_terms_conditions);

        Intent intent = getIntent();
        final User user = intent.getParcelableExtra("user");

        checkBox = findViewById(R.id.checkBox);

        continueButton = (Button) findViewById(R.id.daftarButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sharedpreferences.edit();
                    ed.putString("username", user.getUsername());
                    ed.commit();

                    registerApi = UtilsApi.getApiService();

                    Map<String, String> fields = new HashMap<>();
                    fields.put("username", user.getUsername());
                    fields.put("email", user.getEmail());
                    fields.put("password", user.getPassword());
                    fields.put("name", user.getName());
                    fields.put("birthday", user.getBirthday());
                    fields.put("gender", user.getGender());
                    fields.put("address", user.getAddress());
                    fields.put("role", "klien");

                    Call<User> user = registerApi.registerUser(fields);

                    user.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                openClientDashboard();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("ERROR", "the post process failed");

                            sharedpreferences = getSharedPreferences(MyPREFERENCES,
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor ed = sharedpreferences.edit();
                            ed.clear();
                            ed.commit();

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
        Snackbar.make(checkBox, "Silakan mencentang syarat dan kondisi sebelum mendaftar.",
                Snackbar.LENGTH_LONG).show();
    }

    public void openClientDashboard() {
        Intent intent = new Intent(this, ClientDashboard.class);
        startActivity(intent);

    }
}
