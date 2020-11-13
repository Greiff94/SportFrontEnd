
package no.ntnu.sportsapp.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class UserPrefs {

    private static final String SHARED_PREF_NAME = "shared_preferences";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;

    public UserPrefs(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Activity.MODE_PRIVATE);
        this.prefsEditor = sharedPreferences.edit();
    }

    public String getToken() {
        return sharedPreferences.getString("token", "");
    }

    public void setToken(String token) {
        prefsEditor.putString("token", token).commit();
    }

}