package com.jaysonleon.shuzzle.model.gallery;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jaysonleon.shuzzle.model.article.Article;
import com.jaysonleon.shuzzle.model.article.ArticleRepository;

import java.util.List;

public class SavedArticleViewModel extends AndroidViewModel {
    private SavedArticleRepository repository;
    private LiveData<List<SavedArticle>> allEvents;

    public SavedArticleViewModel(@NonNull Application application) {
        super(application);
        repository = new SavedArticleRepository(application);
        allEvents = repository.getAllSavedArticles();
    }

    public void insert(SavedArticle article) {
        repository.insert(article);
        Log.i("Saved new article", article.getUrl());
    }

    public void update(SavedArticle article) {
        repository.update(article);
    }

    public void delete(SavedArticle article) {
        repository.delete(article);
    }

    public void deleteAllEvents() {
        repository.deleteAllSavedArticles();
    }

    public LiveData<List<SavedArticle>> getAllEvents() {
        return allEvents;
    }
}
