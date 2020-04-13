package com.jaysonleon.shuzzle.model.subreddit;

public enum CategoryEnum {
    MAN_MADE("ManMade"),
    ART("Art"),
    ANIMALS("Animals"),
    NATURE("Nature"),
    FOOD("Food"),
    NSFW("Nsfw");

    private String categoryName;

    CategoryEnum(String value) {
        categoryName = value;
    }

    @Override public String toString() {
        return categoryName;
    }
}
