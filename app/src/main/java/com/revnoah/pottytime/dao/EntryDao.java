package com.revnoah.pottytime.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.revnoah.pottytime.model.Entry;

import java.util.List;

@Dao
public interface EntryDao {
    @Insert
    void insert(Entry entry);

    @Query("SELECT * FROM entry ORDER BY timestamp DESC")
    List<Entry> getAllEntries();
}
