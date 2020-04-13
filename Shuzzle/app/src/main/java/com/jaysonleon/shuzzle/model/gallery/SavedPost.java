package com.jaysonleon.shuzzle.model.gallery;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "saved_table")
public class SavedPost implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private int created;
    private String url;
    private String name;

    public SavedPost(String title, int created, String url, String name) {
        this.title = title;
        this.created = created;
        this.url = url;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getCreated() {
        return created;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
