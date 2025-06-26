package com.example.metapost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChattingPageActivity extends AppCompatActivity {

    // Postojeće komponente
    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private ImageButton sendButton;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    private RelativeLayout normalToolbarLayout, searchToolbarLayout;
    private ImageView backArrow, callIcon, searchIcon, closeSearch, arrowUp, arrowDown;
    private EditText searchInput;

    // NOVE varijable za stanje pretrage
    private List<Integer> searchResultIndices = new ArrayList<>();
    private int currentSearchIndex = -1;

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
        setContentView(R.layout.chatting_page);

        mAuth = FirebaseAuth.getInstance();
        setupUI();
        setupToolbarListeners();
        setupChat();
        setupBottomNavigation();
    }

    private void setupUI() {
        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.chat_toolbar);
        normalToolbarLayout = toolbar.findViewById(R.id.normal_toolbar_layout);
        searchToolbarLayout = toolbar.findViewById(R.id.search_toolbar_layout);
        backArrow = toolbar.findViewById(R.id.back_arrow);
        callIcon = toolbar.findViewById(R.id.call_icon);
        searchIcon = toolbar.findViewById(R.id.search_icon);
        closeSearch = toolbar.findViewById(R.id.close_search);
        arrowUp = toolbar.findViewById(R.id.arrow_up);
        arrowDown = toolbar.findViewById(R.id.arrow_down);
        searchInput = toolbar.findViewById(R.id.search_input);
    }

    private void setupToolbarListeners() {
        backArrow.setOnClickListener(v -> {
            // Kreiramo eksplicitnu namjeru (Intent) da se vratimo na UserListActivity
            Intent intent = new Intent(ChattingPageActivity.this, UserListActivity.class);

            // Ovi flagovi osiguravaju da ne stvaramo duple ekrane i da se navigacija ponaša ispravno.
            // Govore Androidu: "Ako UserListActivity već postoji, samo se prebaci na njega."
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(intent);
            finish(); // Zatvaramo trenutni chat ekran
        });
        searchIcon.setOnClickListener(v -> {
            normalToolbarLayout.setVisibility(View.GONE);
            searchToolbarLayout.setVisibility(View.VISIBLE);
        });
        closeSearch.setOnClickListener(v -> {
            searchInput.setText("");
            searchToolbarLayout.setVisibility(View.GONE);
            normalToolbarLayout.setVisibility(View.VISIBLE);
            messageAdapter.clearHighlight(); // Očisti highlight kada se pretraga zatvori
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Sada strelice pozivaju nove metode za navigaciju
        arrowDown.setOnClickListener(v -> navigateToNextResult());
        arrowUp.setOnClickListener(v -> navigateToPreviousResult());
    }

    // NOVA METODA koja pronalazi sve rezultate
    private void performSearch(String query) {
        searchResultIndices.clear();
        currentSearchIndex = -1;
        messageAdapter.clearHighlight();

        if (query.isEmpty()) {
            return;
        }

        String lowerCaseQuery = query.toLowerCase();
        for (int i = 0; i < messageList.size(); i++) {
            if (messageList.get(i).getText().toLowerCase().contains(lowerCaseQuery)) {
                searchResultIndices.add(i);
            }
        }

        if (!searchResultIndices.isEmpty()) {
            Toast.makeText(this, "Pronađeno " + searchResultIndices.size() + " rezultata", Toast.LENGTH_SHORT).show();
            navigateToNextResult(); // Automatski skoči na prvi rezultat
        } else {
            Toast.makeText(this, "Nema rezultata", Toast.LENGTH_SHORT).show();
        }
    }

    // NOVA METODA za skok na sljedeći rezultat
    private void navigateToNextResult() {
        if (searchResultIndices.isEmpty()) return;
        currentSearchIndex++;
        if (currentSearchIndex >= searchResultIndices.size()) {
            currentSearchIndex = 0; // Vrati se na početak liste ako smo na kraju
        }
        highlightAndScroll();
    }

    // NOVA METODA za skok na prethodni rezultat
    private void navigateToPreviousResult() {
        if (searchResultIndices.isEmpty()) return;
        currentSearchIndex--;
        if (currentSearchIndex < 0) {
            currentSearchIndex = searchResultIndices.size() - 1; // Vrati se na kraj liste ako smo na početku
        }
        highlightAndScroll();
    }

    // NOVA POMOĆNA METODA za označavanje i skrolanje
    private void highlightAndScroll() {
        int messagePosition = searchResultIndices.get(currentSearchIndex);
        messageAdapter.setHighlight(searchInput.getText().toString(), messagePosition);
        chatRecyclerView.smoothScrollToPosition(messagePosition);
    }

    private void setupChat() {
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(messageAdapter);
        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString().trim();
        if (!messageText.isEmpty()) {
            messageList.add(new Message(messageText, true));
            messageAdapter.notifyItemInserted(messageList.size() - 1);
            chatRecyclerView.scrollToPosition(messageList.size() - 1);
            messageInput.setText("");
            new android.os.Handler().postDelayed(() -> receiveBotResponse(messageText.toLowerCase()), 1000);
        }
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
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

    private void receiveBotResponse(String userMessage) {
        String botResponse;
        if (userMessage.equalsIgnoreCase("Dobar dan") || userMessage.equalsIgnoreCase("Bok") || userMessage.equalsIgnoreCase("Pozdrav")) {
            botResponse = "Bok!";
        } else if (userMessage.contains("kako si")) {
            botResponse = "Odlično sam, hvala na pitanju! Spreman pomoći.";
        } else if (userMessage.contains("tko si")) {
            botResponse = "Ja sam MetaBot, tvoj virtualni asistent.";
        } else if (userMessage.contains("šala") || userMessage.contains("vic")) {
            botResponse = "Zašto programeri ne vole prirodu? Ima previše bugova.";
        } else {
            String[] randomResponses = {"Zanimljivo, reci mi više.", "Nisam siguran što da kažem na to.", "Pokušaj me pitati nešto drugo.", "To je izvan mog trenutnog znanja.", "U redu."};
            botResponse = randomResponses[new Random().nextInt(randomResponses.length)];
        }
        messageList.add(new Message(botResponse, false));
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        chatRecyclerView.scrollToPosition(messageList.size() - 1);
    }
}