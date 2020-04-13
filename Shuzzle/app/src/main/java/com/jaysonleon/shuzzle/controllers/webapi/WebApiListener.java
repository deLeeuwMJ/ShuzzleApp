package com.jaysonleon.shuzzle.controllers.webapi;

import com.jaysonleon.shuzzle.model.post.Post;

import java.util.ArrayList;

public interface WebApiListener {
    void onApiStart();
    void onApiSuccess(ArrayList<Post> posts);
    void onApiCancelled();
    void onApiFailure(Exception e);
}
