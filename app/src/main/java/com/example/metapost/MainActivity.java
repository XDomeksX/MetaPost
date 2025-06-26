package com.example.metapost; // ili com.example.metapost

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

import com.example.metapost.R;
import com.example.metapost.RegisterActivity;
import com.example.metapost.UserListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView registerNowText;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private TextView errorTextView; // Deklaracija

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
            startActivity(intent);
            finish();
        }
    }

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

        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.pass_input);
        loginButton = findViewById(R.id.login_button);
        registerNowText = findViewById(R.id.register_now_text);
        progressBar = findViewById(R.id.login_progress_bar);
        errorTextView = findViewById(R.id.error_text); // <-- KLJUČNA LINIJA KOJA JE VJEROJATNO NEDOSTAJALA

        registerNowText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            errorTextView.setVisibility(View.GONE); // Sakrij staru grešku

            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                errorTextView.setText("Unesite email i lozinku");
                errorTextView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Prijava uspješna.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            errorTextView.setText("Autentifikacija neuspješna.");
                            errorTextView.setVisibility(View.VISIBLE);
                        }
                    });
        });
    }
}