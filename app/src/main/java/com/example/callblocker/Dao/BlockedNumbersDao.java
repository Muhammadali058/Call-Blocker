package com.example.callblocker.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.callblocker.Models.BlockedNumbers;

import java.util.List;

@Dao
public interface BlockedNumbersDao {
    @Query("SELECT * FROM BlockedNumbers")
    List<BlockedNumbers> getAll();

    @Query("SELECT * FROM BlockedNumbers where number = :number")
    List<BlockedNumbers> getAllByNumber(String number);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BlockedNumbers blockedNumbers);

    @Delete
    void delete(BlockedNumbers blockedNumbers);

}
