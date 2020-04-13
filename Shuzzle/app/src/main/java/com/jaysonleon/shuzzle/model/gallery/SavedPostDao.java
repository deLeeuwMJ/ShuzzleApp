package com.jaysonleon.shuzzle.model.gallery;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SavedPostDao {

    @Insert
    void insert(SavedPost post);

    @Update
    void update(SavedPost post);

    @Delete
    void delete(SavedPost post);

    @Query("DELETE FROM saved_table")
    void deleteAll();

    @Query("SELECT * FROM saved_table ORDER BY id asc")
    LiveData<List<SavedPost>> getAll();

}
