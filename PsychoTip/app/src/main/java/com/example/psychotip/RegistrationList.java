package com.example.psychotip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationList extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        Button clientButton = findViewById(R.id.client_button);
        Button psychologistButton = findViewById(R.id.psychologist_button);

        clientButton.setOnClickListener(this);
        psychologistButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.client_button:
                Intent clientIntent = new Intent(this, ClientRegistrationPage1.class);
                startActivity(clientIntent);
                break;
            case R.id.psychologist_button:
                Intent psychologistIntent = new Intent(this,
                        PsychologistRegistrationPage1.class);
                startActivity(psychologistIntent);
                break;
            default:
                break;
        }

    }
}