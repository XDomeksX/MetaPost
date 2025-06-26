package com.example.metapost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button registerButton;
    private TextView loginNowText;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

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
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.email_input_register);
        passwordInput = findViewById(R.id.pass_input_register);
        registerButton = findViewById(R.id.register_button);
        loginNowText = findViewById(R.id.login_now_text);
        progressBar = findViewById(R.id.register_progress_bar);

        loginNowText.setOnClickListener(v -> {
            // Vrati korisnika na login ekran
            finish();
        });

        registerButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email, password;
            email = emailInput.getText().toString();
            password = passwordInput.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(RegisterActivity.this, "Unesite email", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(RegisterActivity.this, "Unesite lozinku", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    // I zamijeni cijeli listener koji slijedi s ovim kodom:
                    .addOnCompleteListener(this, task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // ... (logika za uspjeh ostaje ista)
                            Toast.makeText(RegisterActivity.this, "Račun uspješno kreiran.", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            finish();
                        } else {
                            // Registracija NIJE uspješna, provjerimo točan razlog
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegisterActivity.this, "Email adresa je već u upotrebi.", Toast.LENGTH_LONG).show();
                            } else if (exception instanceof FirebaseAuthWeakPasswordException) {
                                // !!!!! NOVI DIO KODA !!!!!
                                // Greška je specifično to da je lozinka preslaba
                                Toast.makeText(RegisterActivity.this, "Lozinka je preslaba. Molimo unesite barem 6 znakova.", Toast.LENGTH_LONG).show();
                            } else {
                                // Za sve ostale greške
                                Toast.makeText(RegisterActivity.this, "Registracija neuspješna: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        });
    }
}