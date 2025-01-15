package org.bihe.faranha.lowercaseimdb.data.file;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private static final String PREF_FILE_NAME = "org.bihe.faranha.assignment2.PREFERENCES_FILE_NAME";

    public static final String PREF_KEY_NAME = "name";
    public static final String PREF_KEY_EMAIL = "email";
    public static final String PREF_KEY_PASSWORD = "password";
    public static final String PREF_KEY_GENDER = "gender";
    public static final String PREF_KEY_ID = "0";

    private static PreferencesManager sInstance;
    private Context mContext;
    private SharedPreferences preferences;

    private PreferencesManager(Context context) {
        mContext = context;
        preferences = mContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static PreferencesManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
        return sInstance;
    }

    public <T> void put(String key, T value) {
        if (value instanceof String) {
            preferences.edit().putString(key, (String) value).apply();
        }
    }

    public <T extends Enum<T>> void putEnum(String key, T enumValue) {
        preferences.edit().putString(key, enumValue.name()).apply();
    }

    public <T> T get(String key, T defaultValue) {
        if (defaultValue instanceof String) {
            return (T) preferences.getString(key, (String) defaultValue);
        }
        return null;
    }

    public <T extends Enum<T>> T getEnum(String key, Class<T> enumClass, T defaultValue) {
        String enumName = preferences.getString(key, null);
        if (enumName != null) {
                return Enum.valueOf(enumClass, enumName);
        }
        return defaultValue;
    }
}

