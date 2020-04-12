package com.jaysonleon.shuzzle.model.article;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "article_table")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
