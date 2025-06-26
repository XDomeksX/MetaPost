package com.example.metapost;

public class Message {
    private String text;
    private boolean isSentByUser; // true ako je poruku poslao korisnik, false ako je bot

    // Konstruktor
    public Message(String text, boolean isSentByUser) {
        this.text = text;
        this.isSentByUser = isSentByUser;
    }

    // Getteri
    public String getText() {
        return text;
    }

    public boolean isSentByUser() {
        return isSentByUser;
    }
}