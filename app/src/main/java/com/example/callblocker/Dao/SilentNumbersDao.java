package com.example.callblocker.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.callblocker.Models.SilentNumbers;

import java.util.List;

@Dao
public interface SilentNumbersDao {
    @Query("SELECT * FROM SilentNumbers")
    List<SilentNumbers> getAll();

    @Query("SELECT * FROM SilentNumbers where number = :number")
    List<SilentNumbers> getAllByNumber(String number);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SilentNumbers silentNumbers);

    @Delete
    void delete(SilentNumbers silentNumbers);

}
