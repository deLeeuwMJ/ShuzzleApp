package com.jaysonleon.shuzzle.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jaysonleon.shuzzle.ui.main.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtil {

    private static final String TAG = SharedPreferencesUtil.class.getSimpleName();

    public static void set(Context context, String key, String value, String name) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
        Log.i(TAG, "Saved to device");
    }

    public static String get(Context context, String key, String name) {
        SharedPreferences prefs = context.getSharedPreferences(name, MODE_PRIVATE);
        Log.i(TAG, "Getting data from device");
        return prefs.getString(key, null);
    }
}
