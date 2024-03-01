package com.example.apichallenge.constant;

public enum Media {
    MUSIC("music"),
    MOVIE("movie"),
    PODCAST("podcast");

    private final String value;

    Media(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
