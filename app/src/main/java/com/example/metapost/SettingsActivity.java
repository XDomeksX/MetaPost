package com.example.metapost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    // Deklaracije za sve komponente
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView nameTextView, emailTextView;
    private CircleImageView profileImageView;
    private Button changeIconButton;
    private ImageView backBtn;
    private SwitchCompat nightModeSwitch, notificationsSwitch;
    private RelativeLayout securityLayout, languageLayout, aboutUsLayout;
    private Button logoutButton;
    private SharedPreferences sharedPreferences;

    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            (Uri uri) -> {
                if (uri != null) {
                    Glide.with(this).load(uri).into(profileImageView);
                    Toast.makeText(this, "Slika profila ažurirana.", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("night_mode", true);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.settings);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Povezivanje svih View elemenata
        nameTextView = findViewById(R.id.user_name_text);
        emailTextView = findViewById(R.id.user_email_text);
        profileImageView = findViewById(R.id.profile_image_settings);
        changeIconButton = findViewById(R.id.change_icon_button);
        backBtn = findViewById(R.id.back_button);
        nightModeSwitch = findViewById(R.id.night_mode_switch);
        notificationsSwitch = findViewById(R.id.notifications_switch);
        securityLayout = findViewById(R.id.security_privacy_layout);
        languageLayout = findViewById(R.id.language_layout);
        aboutUsLayout = findViewById(R.id.about_us_layout);
        logoutButton = findViewById(R.id.logout_button);

        // Postavljanje listenera za gumbe
        backBtn.setOnClickListener(v -> finish());
        changeIconButton.setOnClickListener(v -> mGetContent.launch("image/*"));

        // Poziv metoda za popunjavanje podataka i postavljanje ostalih listenera
        loadUserProfile();
        setupOptionListeners();
    }

    private void loadUserProfile() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            emailTextView.setText(email);

            db.collection("Admins")
                    .whereEqualTo("Email", email)
                    .limit(1)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String adminName = document.getString("fullName");
                                nameTextView.setText(adminName);
                            }
                        } else {
                            nameTextView.setText("Korisnik");
                        }
                    });
        }
    }

    // KOMPLETNA METODA SA SVIM LISTENERIMA
    private void setupOptionListeners() {
        // --- NIGHT MODE ---
        boolean isNightMode = sharedPreferences.getBoolean("night_mode", true);
        nightModeSwitch.setChecked(isNightMode);
        nightModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("night_mode", isChecked);
            editor.apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // --- NOTIFICATIONS ---
        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Notifikacije uključene", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Notifikacije isključene", Toast.LENGTH_SHORT).show();
            }
        });

        // --- SECURITY & PRIVACY ---
        securityLayout.setOnClickListener(v -> {
            Toast.makeText(this, "Prikaz postavki privatnosti (još nije implementirano)", Toast.LENGTH_SHORT).show();
        });

        // --- LANGUAGE ---
        languageLayout.setOnClickListener(v -> {
            Toast.makeText(this, "Promjena jezika još nije dostupna.", Toast.LENGTH_SHORT).show();
        });

        // --- ABOUT US ---
        aboutUsLayout.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, AboutUsActivity.class);
            startActivity(intent);
        });

        // --- LOGOUT ---
        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}