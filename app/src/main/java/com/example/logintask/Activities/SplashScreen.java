package com.example.logintask.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.logintask.databinding.ActivitySplashScreenBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    ActivitySplashScreenBinding binding ;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(auth.getCurrentUser() != null) {
                    Intent intent = new Intent(SplashScreen.this, DashBoard.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },4000);

    }
}