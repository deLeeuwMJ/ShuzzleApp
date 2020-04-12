package com.jaysonleon.shuzzle.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.jaysonleon.shuzzle.R;
import com.jaysonleon.shuzzle.ui.gallery.GalleryActivity;
import com.jaysonleon.shuzzle.ui.main.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start main activity
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // close splash activity
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
