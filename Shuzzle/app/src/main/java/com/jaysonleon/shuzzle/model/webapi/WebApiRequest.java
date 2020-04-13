package com.jaysonleon.shuzzle.model.webapi;

public class WebApiRequest {

    private String subreddit;
    private String title;
    private String sort;
    private int limit;
    private String after;
    private boolean isActive;
    private boolean subredditChanged;

    public WebApiRequest(String subreddit, String title, String sort, int limit, String after) {
        this.subreddit = subreddit;
        this.title = title;
        this.sort = sort;
        this.limit = limit;
        this.after = after;
        this.isActive = false;
        this.subredditChanged = false;
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

    public String getAfter() {
        return after;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isSubredditChanged() {
        return subredditChanged;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public void setSubredditChanged(boolean subredditChanged) {
        this.subredditChanged = subredditChanged;
    }
}
