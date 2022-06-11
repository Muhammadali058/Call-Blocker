package com.example.callblocker.Dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.callblocker.Models.AllowedNumbers;
import com.example.callblocker.Models.BlockedNumbers;
import com.example.callblocker.Models.SilentNumbers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BlockedNumbers.class, SilentNumbers.class, AllowedNumbers.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static ExecutorService executor = Executors.newFixedThreadPool(1);

    public abstract BlockedNumbersDao blockNumbersDao();
    public abstract SilentNumbersDao silentNumbersDao();
    public abstract AllowedNumbersDao allowedNumbersDao();

    private static AppDatabase instance = null;
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "call_blocker")
                                    .allowMainThreadQueries()
                                    .build();
        }

        return instance;
    }
}
