package com.example.payway.activities_and_fragments.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.payway.Data_Managers.Firebase;
import com.example.payway.R;

public class Splashscreen extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000; // Splash screen timeout in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splachscreen);

        // Check if the user is logged in
        if (Firebase.isLoggedIn()) {
            // User is logged in, redirect to MainActivity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close the splash activity so it's not accessible when pressing back button
                }
            }, SPLASH_TIME_OUT);
        } else {
            // User is not logged in, redirect to NumberSignInActivity
            Intent intent = new Intent(Splashscreen.this, number_login.class);
            startActivity(intent);
            finish(); // Close the splash activity so it's not accessible when pressing back button
        }
    }
}
