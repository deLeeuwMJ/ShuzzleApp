package com.jaysonleon.shuzzle.model.article;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "retrieved_table")
public class Article implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String subreddit;
    private String title;
    private int created;
    private String url;
    private String image;

    public Article(String subreddit, String title, int created, String url, String image) {
        this.subreddit = subreddit;
        this.title = title;
        this.created = created;
        this.url = url;
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSubreddit() {
        return subreddit;
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

    public String getImage() {
        return image;
    }
}
