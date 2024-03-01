package com.example.apichallenge.model;

import lombok.Data;

@Data
public class Music {
    private String kind;
    private Integer artistId;
    private Integer trackId;
    private String artistName;
    private String collectionName;
    private String trackName;
    private String collectionCensoredName;
    private String trackCensoredName;
    private String releaseDate;
}
