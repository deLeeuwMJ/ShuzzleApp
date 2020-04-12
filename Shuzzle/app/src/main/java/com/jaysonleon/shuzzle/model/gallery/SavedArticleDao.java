package com.jaysonleon.shuzzle.model.gallery;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jaysonleon.shuzzle.model.article.Article;

import java.util.List;

@Dao
public interface SavedArticleDao {

    @Insert
    void insert(SavedArticle article);

    @Update
    void update(SavedArticle article);

    @Delete
    void delete(SavedArticle article);

    @Query("DELETE FROM saved_table")
    void deleteAllSavedArticles();

    @Query("SELECT * FROM saved_table ORDER BY id desc")
    LiveData<List<SavedArticle>> getAllSavedArticles();

}
