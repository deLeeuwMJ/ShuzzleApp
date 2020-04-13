package com.jaysonleon.shuzzle.model.post;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PostViewModel extends AndroidViewModel {
    private PostRepository repository;
    private LiveData<List<Post>> livePostList;

    public PostViewModel(@NonNull Application application) {
        super(application);
        repository = new PostRepository(application);
        livePostList = repository.getList();
    }

    public void insert(Post post) {
        repository.insert(post);
    }

    public void update(Post post) {
        repository.update(post);
    }

    public void delete(Post post) {
        repository.delete(post);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<Post>> getList() {
        return livePostList;
    }
}
