package com.driver;

import java.util.Date;

public class Message
{
    private int id;
    private String content;
    private Date timestamp;
    private User sender; // Add sender field

    public Message(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public Message(int id, String content, Date timestamp, User sender) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.sender = sender;
    }

    public Message() {
    }

    // Add getter and setter for sender
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

        public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
