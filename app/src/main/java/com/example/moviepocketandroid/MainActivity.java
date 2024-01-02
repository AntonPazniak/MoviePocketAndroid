package com.example.moviepocketandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFirstRun()) {
            Intent intent = new Intent(this, FirstRunActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
            finish();
        }

    }


    private boolean isFirstRun() {
        SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstRun", false);
            editor.apply();
        }

        return isFirstRun;
    }

}
