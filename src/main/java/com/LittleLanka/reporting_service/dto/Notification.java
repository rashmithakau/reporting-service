package com.LittleLanka.reporting_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification {
    private String content;
    private Date timestamp;
    // Add other fields as needed

    // Default constructor is REQUIRED for Jackson
    public Notification() {}

    public Notification(String content) {
        this.content = content;
        this.timestamp = new Date();
    }

    // Proper getters and setters for all fields
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
