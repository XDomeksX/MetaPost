package com.example.metapost;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem; // Uvjerimo se da je ovaj import tu

import androidx.annotation.NonNull; // I ovaj
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth; // <-- 1. DODAN NOVI IMPORT

public class ChattingPageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // <-- 2. DODANA DEKLARACIJA ZA FIREBASE AUTH

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_page);

        mAuth = FirebaseAuth.getInstance(); // <-- 3. INICIJALIZACIJA FIREBASE AUTH

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    return true;
                } else if (id == R.id.nav_logout) { // Tvoj ID je točan
                    // --- ISPRAVAK JE OVDJE ---
                    mAuth.signOut(); // <-- 4. KLJUČNA LINIJA: ODJAVI KORISNIKA!
                    Intent intent = new Intent(ChattingPageActivity.this, MainActivity.class);
                    // Dodajemo flagove da se sigurno očisti povijest ekrana
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.nav_settings) {
                    Intent intent = new Intent(ChattingPageActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    // Ovdje ne stavljamo finish() da se korisnik može vratiti iz postavki
                    return true;
                }

                return false;
            }
        });
    }
}