package com.example.apichallenge.model;

import lombok.Data;

import java.util.List;

@Data
public class Book {
    private String title;
    private String subtitle;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private Integer pageCount;
    private List<String> categories;
}
