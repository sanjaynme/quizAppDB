package np.edu.nast.demoapp.quizapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

public class SharedPreferenceManager {

    private SharedPreferences prefs;

    public SharedPreferenceManager(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setKeyValues(@NonNull String key, @Nullable String value) {
        prefs.edit().putString(key, value).apply();
    }

    public void setKeyValues(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    public void setKeyValues(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }

    public void setKeyValues(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public String getStringValues(String key) {
        return prefs.getString(key, "");
    }

    public int getIntValues(String key) {
        int nullValue = 0;
        return prefs.getInt(key, nullValue);
    }


    public long getLongValues(String key) {
        long nullValue = 0;
        return prefs.getLong(key, nullValue);
    }

    public boolean getBoolValues(String key) {
        return prefs.getBoolean(key, false);
    }

    public Map<String, ?> getAllVal() {
        return prefs.getAll();
    }

    public void clearData() {
        prefs.edit().clear().apply();
    }

    @SuppressLint("ApplySharedPref")
    public void removeValueForKey(String key) {
        prefs.edit().remove(key).commit();
    }
}
