package com.ds.quicknotes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "note_table")
public class Note {

    //Attributes
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int importance;

    public Note(){

    }

    public Note(String title, String description, int importance) {
        this.title = title;
        this.description = description;
        this.importance = importance;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
}