package com.example.callblocker.Models;

import androidx.annotation.NonNull;

public class Contacts {
    public String name;
    public String phoneNumber;

    public Contacts() {
    }

    public Contacts(String name, String phoneNumber ) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
