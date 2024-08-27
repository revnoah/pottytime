package com.revnoah.pottytime.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String acronym;
    private String description;
    private int ordering = 0;

    public Category(String acronym, String description, int ordering) {
        this.acronym = acronym;
        this.description = description;
        this.ordering = ordering;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAcronym() { return acronym; }
    public void setAcronym(String acronym) { this.acronym = acronym; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getOrdering() { return ordering; }
    public void setOrdering(int ordering) { this.ordering = ordering; }
}
