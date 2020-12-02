
package no.ntnu.sportsapp.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class UserPrefs {

    private static final String SHARED_PREF_NAME = "shared_preferences";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;
    private int lBtnState;

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

    public String getUid() {
        return sharedPreferences.getString("uid", "");
    }

    public void setUid(String uid) {
        prefsEditor.putString("uid", uid).commit();
    }

    public String getPwd() {
        return sharedPreferences.getString("pwd", "");
    }

    public void setPwd(String pwd) {
        prefsEditor.putString("pwd", pwd).commit();
    }

    public void setButtonState(int btnState) {
        prefsEditor.putInt("buttonState", btnState).commit();
    }

    public int getButtonState () {
        return sharedPreferences.getInt("buttonState", lBtnState);
    }
}