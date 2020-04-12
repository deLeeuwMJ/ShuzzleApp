package com.jaysonleon.shuzzle.model.webapi;

import android.content.Context;
import android.util.Log;

import com.jaysonleon.shuzzle.controllers.article.WebApiListener;

public class WebApi extends AbstractWebApi {

    private static final String BUILD_ERROR = WebApi.class.getSimpleName();
    private static final String BASE_URL = "http://jaysonleon.online/api/v1/json.php";

    private final String subreddit;
    private final String title;
    private final String sort;
    private final int limit;

    private WebApi(WebApi.Builder builder) {
        super(builder.context, builder.listener);
        this.subreddit = builder.subreddit;
        this.title = builder.title;
        this.sort = builder.sort;
        this.limit = builder.limit;
    }

    @Override
    protected String constructURL() {
        StringBuilder stringBuilder = new StringBuilder(BASE_URL);
        stringBuilder.append("?subreddit=" + this.subreddit);
        stringBuilder.append("&title=" + this.title);
        stringBuilder.append("&sort=" + this.sort);
        stringBuilder.append("&limit=" + this.limit);

        return stringBuilder.toString();
    }

    public static class Builder {
        private WebApiListener listener;
        private Context context;
        private String subreddit;
        private String title;
        private String sort;
        private int limit;

        public Builder() {
            this.listener = null;
            this.context = null;
            this.subreddit = null;
            this.title = null;
            this.sort = null;
            this.limit = 0;
        }

        public WebApi.Builder withListener(WebApiListener listener) {
            this.listener = listener;
            return this;
        }

        public WebApi.Builder context(Context context) {
            this.context = context;
            return this;
        }

        public WebApi.Builder subreddit(String subreddit) {
            this.subreddit = subreddit;
            return this;
        }

        public WebApi.Builder title(String title) {
            this.title = title;
            return this;
        }

        public WebApi.Builder sort(String sort) {
            this.sort = sort;
            return this;
        }

        public WebApi.Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public WebApi build() {
            if (this.limit <= 0 || this.limit > 100) {
                Log.e(BUILD_ERROR, "Limit must be between 1 and 100");
                return null;
            } else if (this.listener == null) {
                Log.e(BUILD_ERROR, "Listener can't be null");
                return null;
            } else if (this.subreddit == null) {
                Log.e(BUILD_ERROR, "Subreddit can't be null");
                return null;
            } else if (this.title == null) {
                Log.e(BUILD_ERROR, "Title can't be null");
                return null;
            } else if (this.sort == null) {
                Log.e(BUILD_ERROR, "Sort can't be null");
                return null;
            } else if (this.context == null) {
                Log.e(BUILD_ERROR, "Context can't be null");
                return null;
            } else {
                return new WebApi(this);
            }
        }
    }
}
