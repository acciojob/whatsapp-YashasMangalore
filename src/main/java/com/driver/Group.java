package com.driver;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Group
{
    private String name;
    private int numberOfParticipants;
    private User admin;
    private List<User> participants;
    private Date lastModified;

    public Group(String name, int numberOfParticipants) {
        this.name = name;
        this.numberOfParticipants = numberOfParticipants;
        this.participants = new ArrayList<>();
    }

    public Group() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void addParticipant(User user) {
        participants.add(user);
    }

    public void removeParticipant(User user) {
        participants.remove(user);
    }

    public boolean isAdmin(User user) {
        return admin != null && admin.equals(user);
    }

    public void changeAdmin(User newAdmin) {
        admin = newAdmin;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
