package com.revnoah.pottytime.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.revnoah.pottytime.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category category);

    @Query("SELECT * FROM category ORDER BY ordering ASC")
    List<Category> getAllCategories();
}
