package com.example.metapost;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView registerNowText;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Provjeri je li korisnik već prijavljen i ažuriraj UI.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), ChattingPageActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicijalizacija Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Povezivanje s view elementima
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.pass_input);
        loginButton = findViewById(R.id.login_button);
        registerNowText = findViewById(R.id.register_now_text);
        progressBar = findViewById(R.id.login_progress_bar);

        // Listener za tekst za registraciju
        registerNowText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Listener za gumb za prijavu
        loginButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email, password;
            email = emailInput.getText().toString();
            password = passwordInput.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(MainActivity.this, "Unesite email", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(MainActivity.this, "Unesite lozinku", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // ... unutar loginButton.setOnClickListener ...

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Prijava uspješna, navigiraj na UserListActivity
                            Toast.makeText(MainActivity.this, "Prijava uspješna.", Toast.LENGTH_SHORT).show();

                            // !!!!! PROMIJENI OVU LINIJU !!!!!
                            Intent intent = new Intent(getApplicationContext(), UserListActivity.class);

                            startActivity(intent);
                            finish();
                        } else {
                            // Ako prijava ne uspije, prikaži poruku korisniku.
                            Toast.makeText(MainActivity.this, "Autentifikacija neuspješna.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}