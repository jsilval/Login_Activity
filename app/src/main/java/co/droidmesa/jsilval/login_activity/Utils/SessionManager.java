package co.droidmesa.jsilval.login_activity.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import co.droidmesa.jsilval.login_activity.LoginActivity;

/**
 * Created by jsilval on 11/01/17.
 */

public class SessionManager {
    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Contex
    private Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared pref file name
    private static final String PREF_NAME = "userPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "isLoggedIn";

    // User name (make variable public to access from outside)
    private static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    private static final String KEY_TOKEN = "token";

    // Session provider
    private static final String PROVIDER = "provider";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String name, String token, String provider) {
        // Storing login values as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing token in pref
        editor.putString(KEY_TOKEN, token);

        // Storing provider type in pref
        editor.putString(PROVIDER, provider);

        // commit changes
        editor.commit();
    }

    /**
     * Get Stored session data
     */

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user =  new HashMap<>();

        // User name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // User token
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));

        // User session provider
        user.put(PROVIDER, pref.getString(PROVIDER, null));

        return user;
    }

    /**
     * Check the method will check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */

    public void checkLogin() {
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    /**
     * Clear session details
     */

    public void logoutUser(){

        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login: Get Login State
     */

    private boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
