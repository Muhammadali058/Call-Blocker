package com.example.callblocker.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.callblocker.Models.AllowedNumbers;

import java.util.List;

@Dao
public interface AllowedNumbersDao {
    @Query("SELECT * FROM AllowedNumbers")
    List<AllowedNumbers> getAll();

    @Query("SELECT * FROM AllowedNumbers where number = :number")
    List<AllowedNumbers> getAllByNumber(String number);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AllowedNumbers allowedNumbers);

    @Delete
    void delete(AllowedNumbers allowedNumbers);

}
