package com.jaysonleon.shuzzle.model.webapi;

public class WebApiRequest {

    private String subreddit;
    private String title;
    private String sort;
    private int limit;

    public WebApiRequest(String subreddit, String title, String sort, int limit) {
        this.subreddit = subreddit;
        this.title = title;
        this.sort = sort;
        this.limit = limit;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getTitle() {
        return title;
    }

    public String getSort() {
        return sort;
    }

    public int getLimit() {
        return limit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }
}
