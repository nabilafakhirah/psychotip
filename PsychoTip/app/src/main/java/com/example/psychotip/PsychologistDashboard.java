package com.example.psychotip;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PsychologistDashboard extends AppCompatActivity
        implements PsychologistHomeFragment.OnFragmentInteractionListener,
        PsychologistCalendarFragment.OnFragmentInteractionListener,
        PsychologistChatFragment.OnFragmentInteractionListener,
        PsychologistNotificationFragment.OnFragmentInteractionListener,
        PsychologistProfileFragment.OnFragmentInteractionListener,
        PsychologistRoutineScheduleFragment.OnFragmentInteractionListener {

    Api qotdApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment defaultFragment = new PsychologistHomeFragment();
        setContentView(R.layout.activity_psychologist_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        qotdApi = UtilsApi.getApiService();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_psychologist,
                defaultFragment).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new PsychologistHomeFragment();
                            break;
                        case R.id.navigation_calendar:
                            selectedFragment = new PsychologistCalendarFragment();
                            break;
                        case R.id.navigation_chat:
                            selectedFragment = new PsychologistChatFragment();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new PsychologistNotificationFragment();
                            break;
                        case R.id.navigation_profile:
                            selectedFragment = new PsychologistProfileFragment();
                            break;
                        default:
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_container_psychologist,
                    selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_favorite) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences(
                ClientTermsAndConditions.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

}
