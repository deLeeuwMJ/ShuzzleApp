package com.jaysonleon.shuzzle.model.article;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {
    private ArticleRepository repository;
    private LiveData<List<Article>> allEvents;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        repository = new ArticleRepository(application);
        allEvents = repository.getAllArticles();
    }

    public void insert(Article article) {
        repository.insert(article);
    }

    public void update(Article article) {
        repository.update(article);
    }

    public void delete(Article article) {
        repository.delete(article);
    }

    public void deleteAllEvents() {
        repository.deleteAllEvents();
    }

    public LiveData<List<Article>> getAllEvents() {
        return allEvents;
    }
}
