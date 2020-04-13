package com.jaysonleon.shuzzle.util;

import android.app.Activity;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtil {

    public static void showSnackBar(Activity activity, String message){
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, 3000).show();
    }
}
