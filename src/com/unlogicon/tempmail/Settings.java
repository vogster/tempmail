package com.unlogicon.tempmail;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nik on 14.08.14.
 */
public class Settings {

    private SharedPreferences preferences;

    public static final String KEY_EMAIL = "email";
    public static final String KEY_EMAIL_HASH = "email_hash";


    public Settings(Activity activity) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public void setEmail(String email) {
        preferences.edit().putString(KEY_EMAIL, email).commit();
    }

    public String getEmail() {
        return preferences.getString(KEY_EMAIL, null);
    }

    public void setHash(String hash) {
        preferences.edit().putString(KEY_EMAIL_HASH, hash).commit();
    }

    public String getHash () {
        return preferences.getString(KEY_EMAIL_HASH, null);
    }
}
