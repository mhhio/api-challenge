package com.example.apichallenge.service;

import com.example.apichallenge.client.GoogleBooksClient;
import com.example.apichallenge.config.APIConfiguration;
import com.example.apichallenge.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

@Service
@AllArgsConstructor
@Slf4j
public class BookService {
    private final GoogleBooksClient googleBooksClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final APIConfiguration apiConfiguration;
    @Cacheable("books")
    @CircuitBreaker(name = "bookServiceCircuitBreaker", fallbackMethod = "fallbackGetBooks")
    public List<Book> getBooks(String query) {
        List<Book> books = new ArrayList<>();
        String response = googleBooksClient.searchBooks("intitle:" + query, apiConfiguration.getGoogleBookApiLimit());
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            log.error("invalid json response {} with err:",response,e);
            //todo need to handle error
            throw new RuntimeException(e);
        }
        if (rootNode.has("items")) {
            for (JsonNode item : rootNode.path("items")) {
                Book book = new Book();
                JsonNode volumeInfo = item.path("volumeInfo");
                book.setTitle(volumeInfo.path("title").asText());
                book.setSubtitle(volumeInfo.path("subtitle").asText());
                book.setAuthors(parseAuthors(volumeInfo.path("authors")));
                book.setPublisher(volumeInfo.path("publisher").asText());
                book.setPublishedDate(volumeInfo.path("publishedDate").asText());
                book.setDescription(volumeInfo.path("description").asText());
                book.setPageCount(volumeInfo.path("pageCount").asInt());
                book.setCategories(parseCategories(volumeInfo.path("categories")));
                books.add(book);
            }
        }
        books.sort(Comparator.comparing(Book::getTitle));
        return books;
    }

    private List<String> parseCategories(JsonNode categoriesNode) {
        List<String> categories = new ArrayList<>();
        if (categoriesNode.isArray()) {
            for (final JsonNode objNode : categoriesNode) {
                categories.add(objNode.asText());
            }
        }
        return categories;
    }

    private List<String> parseAuthors(JsonNode authorsNode) {
        List<String> authors = new ArrayList<>();
        if (authorsNode.isArray()) {
            for (final JsonNode objNode : authorsNode) {
                authors.add(objNode.asText());
            }
        }
        return authors;
    }

    private List<Book> fallbackGetBooks(String query, Throwable t) {
        //todo add search in cache
        return new ArrayList<>();
    }
}
