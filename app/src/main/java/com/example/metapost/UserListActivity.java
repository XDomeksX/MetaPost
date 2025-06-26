package com.example.metapost;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class UserListActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        mAuth = FirebaseAuth.getInstance();

        CardView metapodChatOption = findViewById(R.id.metapod_chat_option);
        metapodChatOption.setOnClickListener(v -> {
            Intent intent = new Intent(UserListActivity.this, ChattingPageActivity.class);
            startActivity(intent);
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_logout) {
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            } else if (id == R.id.nav_settings) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    // !!!!! DODAJ OVU METODU !!!!!
    @Override
    protected void onResume() {
        super.onResume();
        // Osiguraj da je "Home" uvijek odabran kada se vratimo na ovaj ekran
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
}