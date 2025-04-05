package com.LittleLanka.reporting_service.dto;

public class NotificationMessage {
    private String content;

    // Constructor, getters, and setters
    public NotificationMessage() {}

    public NotificationMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
