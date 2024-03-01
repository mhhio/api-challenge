package com.example.apichallenge.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "google-books", url = "https://www.googleapis.com/books/v1")
public interface GoogleBooksClient {
    @GetMapping("/volumes")
    String searchBooks(@RequestParam("q") String query,@RequestParam("maxResults")Integer maxResults);
}
