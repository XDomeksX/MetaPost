package com.example.metapost; // zamijeni prema svom paketu

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class ChattingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_page); // povezujemo s chatting_page.xml

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Označi Home kao aktivni tab
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    // Ostajemo na trenutnoj aktivnosti
                    return true;
                } else if (id == R.id.nav_logout) {
                    Intent intent = new Intent(ChattingPageActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // da se ne možeš vratiti back gumbom
                    return true;
                } else if (id == R.id.nav_settings) {
                    Intent intent = new Intent(ChattingPageActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });
    }
}