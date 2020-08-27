package com.example.food_fantasy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.food_fantasy.ui.login.LoginActivity;

public class splash extends AppCompatActivity {
    public static SharedPreferences pref;
    private int slpashTime = 3000;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler.postDelayed(new Runnable() {
            public void run() {
                startMainActivity();
            }
        }, 3000);
        pref = getApplicationContext().getSharedPreferences("api_hit", 0); // 0 - for private mode

    }

    private void startMainActivity(){
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }
}
