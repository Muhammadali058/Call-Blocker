package com.example.callblocker.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BlockedNumbers {
    @PrimaryKey(autoGenerate = true)
    long id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "number")
    public String number;

    public BlockedNumbers() {
    }

    public BlockedNumbers(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public BlockedNumbers(long id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

}
