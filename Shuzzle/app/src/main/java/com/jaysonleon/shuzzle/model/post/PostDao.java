package com.jaysonleon.shuzzle.model.post;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {

    @Insert
    void insert(Post post);

    @Update
    void update(Post post);

    @Delete
    void delete(Post post);

    @Query("DELETE FROM retrieved_table")
    void deleteAll();

    @Query("SELECT * FROM retrieved_table ORDER BY id asc")
    LiveData<List<Post>> getAll();

}
