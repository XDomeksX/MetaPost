package com.example.metapost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import android.view.MenuItem;


public class UserListActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // Inicijalizacija Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Povezivanje s CardView za chat
        CardView metapodChatOption = findViewById(R.id.metapod_chat_option);
        metapodChatOption.setOnClickListener(v -> {
            Intent intent = new Intent(UserListActivity.this, ChattingPageActivity.class);
            startActivity(intent);
        });

        // Povezivanje i postavljanje donje navigacije
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Već smo na "home" ekranu, ne radi ništa
                return true;
            } else if (id == R.id.nav_logout) {
                // Logika za odjavu
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            } else if (id == R.id.nav_settings) {
                // Logika za postavke
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
}