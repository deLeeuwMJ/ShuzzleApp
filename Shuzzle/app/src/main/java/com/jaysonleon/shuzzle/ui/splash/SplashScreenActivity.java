package com.jaysonleon.shuzzle.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jaysonleon.shuzzle.ui.main.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start main activity
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // close splash activity
        finish();
    }
}
