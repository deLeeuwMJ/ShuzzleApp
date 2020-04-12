package com.jaysonleon.shuzzle.model.article;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ArticleDao {

    @Insert
    void insert(Article article);

    @Update
    void update(Article article);

    @Delete
    void delete(Article article);

    @Query("DELETE FROM article_table")
    void deleteAllArticles();

    @Query("SELECT * FROM article_table ORDER BY id DESC")
    LiveData<List<Article>> getAllArticles();

}
