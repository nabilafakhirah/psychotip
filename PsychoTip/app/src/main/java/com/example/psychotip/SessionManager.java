package com.example.psychotip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.psychotip.model.LoginPage;

import java.util.HashMap;

import retrofit2.http.HEAD;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int privateMode = 0;

    // Shared pref file name
    private static final String pref_name = "PsychoTipPref";

    // All Shared Preferences Keys
    private static final String is_logged_in = "IsLoggedIn";

    public static final String key_username = "username";
    public static final String key_password = "password";

    public SessionManager(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(pref_name, privateMode);
        editor = pref.edit();
    }

    /**
     * Create login session.
     */
    public void createLoginSession(String username, String password) {
        editor.putBoolean(is_logged_in, true);

        editor.putString(key_username, username);
        editor.putString(key_password, password);

        editor.commit();
    }

    /**
     * Check login method wil check user login status.
     * If false it will redirect user to login page,
     * Else won't do anything.
     */
    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(context, LoginPage.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);
        }
    }

    /**
     * Get stored session data.
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(key_username, pref.getString(key_username, null));
        user.put(key_password, pref.getString(key_password, null));
        return user;
    }

    /**
     * Clear session details.
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Landing Page
        Intent i = new Intent(context, LandingPage.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Starting Login Page
        context.startActivity(i);
    }

    /**
     * Quick check for login.
     **/
    public boolean isLoggedIn() {
        return pref.getBoolean(is_logged_in, false);
    }
}
