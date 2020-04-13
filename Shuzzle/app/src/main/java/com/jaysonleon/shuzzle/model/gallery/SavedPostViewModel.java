package com.jaysonleon.shuzzle.model.gallery;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SavedPostViewModel extends AndroidViewModel {
    private SavedPostRepository repository;
    private LiveData<List<SavedPost>> livePostList;

    public SavedPostViewModel(@NonNull Application application) {
        super(application);
        repository = new SavedPostRepository(application);
        livePostList = repository.getList();
    }

    public void insert(SavedPost post) {
        repository.insert(post);
        Log.i("Saved new post", post.getUrl());
    }

    public void update(SavedPost post) {
        repository.update(post);
    }

    public void delete(SavedPost post) {
        repository.delete(post);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<SavedPost>> getList() {
        return livePostList;
    }
}
