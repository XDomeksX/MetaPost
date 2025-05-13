package com.example.metapost; // zamijeni ako je tvoj paket drukčiji

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings); // povezuje se s tvojim settings.xml
        ImageView backBtn = findViewById(R.id.back_button);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ChattingPageActivity.class);
                startActivity(intent);
                finish(); // zatvori trenutni ekran
            }
        });


    }
}