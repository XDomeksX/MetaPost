package com.example.metapost;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Test credentials
    private static final String TEST_USERNAME = "admin";
    private static final String TEST_PASSWORD = "1234";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Get views
        EditText usernameInput = findViewById(R.id.username_input);
        EditText passwordInput = findViewById(R.id.pass_input);
        Button loginButton = findViewById(R.id.login_button);

        // Set login button action
        loginButton.setOnClickListener(v -> {
            String enteredUsername = usernameInput.getText().toString().trim();
            String enteredPassword = passwordInput.getText().toString();

            if (enteredUsername.equals(TEST_USERNAME) && enteredPassword.equals(TEST_PASSWORD)) {
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                // TODO: Navigate to next screen or activity
                Intent intent = new Intent(MainActivity.this, ChattingPageActivity.class);
                startActivity(intent);
                finish(); // zatvori login ekran da se ne možeš vratiti "back" gumbom

            } else {
                Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
