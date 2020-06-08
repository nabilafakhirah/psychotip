package com.example.psychotip;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClientDashboard extends AppCompatActivity implements
        ClientHomeFragment.OnFragmentInteractionListener,
        ClientCalendarFragment.OnFragmentInteractionListener,
        ClientChatFragment.OnFragmentInteractionListener,
        ClientNotificationFragment.OnFragmentInteractionListener,
        ClientProfileFragment.OnFragmentInteractionListener,
        ClientCounselingCategoryFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment defaultFragment = new ClientHomeFragment();
        setContentView(R.layout.activity_client_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
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
                            selectedFragment = new ClientHomeFragment();
                            break;
                        case R.id.navigation_calendar:
                            selectedFragment = new ClientCalendarFragment();
                            break;
                        case R.id.navigation_chat:
                            selectedFragment = new ClientCounselingCategoryFragment();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new ClientNotificationFragment();
                            break;
                        case R.id.navigation_profile:
                            selectedFragment = new ClientProfileFragment();
                            break;
                        default:
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
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

}
