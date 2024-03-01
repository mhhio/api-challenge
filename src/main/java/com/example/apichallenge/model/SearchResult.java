package com.example.apichallenge.model;

import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
    private List<Music> musics;
    private List<Book> books;
}
