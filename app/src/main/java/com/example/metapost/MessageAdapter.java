package com.example.metapost;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;
    private String searchQuery = "";
    private int highlightedPosition = -1; // Pozicija trenutno označenog retka

    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_text);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        return message.isSentByUser() ? VIEW_TYPE_SENT : VIEW_TYPE_RECEIVED;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        String messageText = message.getText();

        // Logika za highlightanje SAMO TRENUTNO ODABRANOG retka
        if (position == highlightedPosition && !searchQuery.isEmpty()) {
            SpannableString spannableString = new SpannableString(messageText);
            BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.YELLOW);

            // Pretvori oba stringa u mala slova za pouzdanu pretragu
            String lowerCaseMessage = messageText.toLowerCase();
            String lowerCaseQuery = searchQuery.toLowerCase();

            int startIndex = lowerCaseMessage.indexOf(lowerCaseQuery);
            if (startIndex != -1) {
                int endIndex = startIndex + lowerCaseQuery.length();
                spannableString.setSpan(backgroundColorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            holder.messageText.setText(spannableString);
        } else {
            // Ako nije označen, prikaži normalan tekst
            holder.messageText.setText(messageText);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    // NOVA METODA za postavljanje highlighta
    public void setHighlight(String query, int position) {
        this.searchQuery = query;
        this.highlightedPosition = position;
        notifyDataSetChanged(); // Obavijesti adapter da se ponovno iscrta
    }

    // NOVA METODA za čišćenje highlighta
    public void clearHighlight() {
        this.searchQuery = "";
        this.highlightedPosition = -1;
        notifyDataSetChanged(); // Obavijesti adapter da se ponovno iscrta
    }
}