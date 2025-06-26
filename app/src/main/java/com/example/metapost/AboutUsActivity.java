package com.example.metapost;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class AboutUsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Postavljanje ispravne teme prije svega
        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("night_mode", true);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_about_us);

        // Povezivanje gumba za natrag i postavljanje listenera
        ImageView backButton = findViewById(R.id.about_back_button);
        backButton.setOnClickListener(v -> {
            // finish() zatvara trenutni ekran i vraća na prethodni (Settings)
            finish();
        });
    }
}