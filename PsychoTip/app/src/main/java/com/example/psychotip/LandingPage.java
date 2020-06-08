package com.example.psychotip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.psychotip.model.LoginPage;

public class LandingPage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Button loginButton = findViewById(R.id.masuk_button);
        Button registerButton = findViewById(R.id.daftar_button);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.masuk_button:
                Intent loginIntent = new Intent(this, LoginPage.class);
                startActivity(loginIntent);
                break;
            case R.id.daftar_button:
                Intent registerIntent = new Intent(this, RegistrationList.class);
                startActivity(registerIntent);
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
