package com.example.metapost;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable; // Added for TextWatcher
import android.text.TextWatcher; // Added for TextWatcher
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Import the 'validacija' class from its new package
import com.example.metapost.utils.validacija; // Adjust package based on where you put it
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    // TEST_USERNAME and TEST_PASSWORD are no longer needed here as validacija class handles them.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Get views
        EditText usernameInput = findViewById(R.id.username_input);
        EditText passwordInput = findViewById(R.id.pass_input);
        Button loginButton = findViewById(R.id.login_button);

        // --- Start of Optional UI/UX Improvements ---
        // Initially disable the login button.
        // It will be enabled only when both username and password fields are filled.
        loginButton.setEnabled(false);

        // Add TextChangedListeners to both input fields to enable/disable the login button.
        // This ensures the user can only attempt login when both fields have text.
        TextWatcher loginFieldWatcher = new LoginFieldWatcher(usernameInput, passwordInput, loginButton);
        usernameInput.addTextChangedListener(loginFieldWatcher);
        passwordInput.addTextChangedListener(loginFieldWatcher);
        // --- End of Optional UI/UX Improvements ---


        // Set login button action
        loginButton.setOnClickListener(v -> {
            String enteredUsername = usernameInput.getText().toString().trim();
            String enteredPassword = passwordInput.getText().toString();

            // Use the 'validacija' class to check credentials
            if (validacija.isValidCredentials(enteredUsername, enteredPassword)) {
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                // Navigate to next screen or activity
                Intent intent = new Intent(MainActivity.this, ChattingPageActivity.class);
                startActivity(intent);
                finish(); // Close login screen so you can't go back with the "back" button

            } else {
                Snackbar.make(findViewById(R.id.main), "Invalid username or password", Snackbar.LENGTH_SHORT).show();
            }
        });

        // Apply window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Inner class to implement TextWatcher for enabling/disabling the login button.
     * The login button is enabled only when both username and password fields are non-empty.
     */
    private class LoginFieldWatcher implements TextWatcher {
        private EditText usernameInput;
        private EditText passwordInput;
        private Button loginButton;

        // Constructor to inject the required views
        public LoginFieldWatcher(EditText usernameInput, EditText passwordInput, Button loginButton) {
            this.usernameInput = usernameInput;
            this.passwordInput = passwordInput;
            this.loginButton = loginButton;
        }

        // Methods of TextWatcher interface
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not needed for this functionality
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not needed for this functionality
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Check if both username and password fields have content
            boolean usernameFilled = !validacija.isFieldEmpty(usernameInput.getText().toString());
            boolean passwordFilled = !validacija.isFieldEmpty(passwordInput.getText().toString());

            // Enable the login button only if both fields are filled
            loginButton.setEnabled(usernameFilled && passwordFilled);
        }
    }
}
