package com.example.callblocker.Models;

import java.util.List;

public class Sections {
    String date;
    List<CallLogs> itemsList;

    public Sections() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CallLogs> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<CallLogs> itemsList) {
        this.itemsList = itemsList;
    }
}
