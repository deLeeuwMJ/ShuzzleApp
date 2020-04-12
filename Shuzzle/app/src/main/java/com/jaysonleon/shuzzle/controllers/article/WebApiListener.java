package com.jaysonleon.shuzzle.controllers.article;

import com.jaysonleon.shuzzle.model.article.Article;

import java.util.ArrayList;

public interface WebApiListener {
    void onApiStart();
    void onApiSuccess(ArrayList<Article> events);
    void onApiCancelled();
    void onApiFailure(Exception e);
}
