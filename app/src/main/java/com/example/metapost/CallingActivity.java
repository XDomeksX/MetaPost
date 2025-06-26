package com.example.metapost;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class CallingActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("night_mode", true);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(R.layout.activity_calling);

        ImageButton hangUpButton = findViewById(R.id.hang_up_button);

        // Inicijaliziraj i pokreni zvuk
        mediaPlayer = MediaPlayer.create(this, R.raw.calling_sound);
        mediaPlayer.setLooping(true); // Ponavljaj zvuk
        mediaPlayer.start();

        // Listener za gumb za prekid poziva
        hangUpButton.setOnClickListener(v -> {
            finish(); // Samo zatvori aktivnost
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Oslobodi resurse kada se aktivnost uništi
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}