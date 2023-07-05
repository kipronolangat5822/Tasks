package com.example.airtime;

public class User {
    private String username,emailaddress,phoneNumber,uuid;

    public User(String username, String emailaddress, String phoneNumber, String uuid) {
        this.username = username;
        this.emailaddress = emailaddress;
        this.phoneNumber = phoneNumber;
        this.uuid = uuid;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
