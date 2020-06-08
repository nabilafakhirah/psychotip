package com.example.psychotip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContactUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Button contactUsButton = findViewById(R.id.about_us_button);

        contactUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutUsPage();
            }
        });
    }

    private void openAboutUsPage() {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
    }

}