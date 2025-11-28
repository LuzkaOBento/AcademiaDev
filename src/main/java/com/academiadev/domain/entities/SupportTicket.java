package com.academiadev.domain.entities;

public class SupportTicket {
    private static int idCounter = 1;
    private int id;
    private String title;
    private String message;

    public SupportTicket(String title, String message) {
        this.id = idCounter++;
        this.title = title;
        this.message = message;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "Ticket #" + id + ": " + title + " - " + message;
    }
}