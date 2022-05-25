package com.example.callblocker.Models;

import androidx.annotation.NonNull;

public class CallLogs {
    String name;
    String number;
    int  type; // 1 received, 2 dialed, 5 missed
    long date;
    long duration;

    public CallLogs() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @NonNull
    @Override
    public String toString() {
        return number + " = " + type;
    }
}
