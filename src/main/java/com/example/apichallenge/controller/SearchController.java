package com.example.apichallenge.controller;

import com.example.apichallenge.model.Book;
import com.example.apichallenge.model.Music;
import com.example.apichallenge.model.SearchResult;
import com.example.apichallenge.service.BookService;
import com.example.apichallenge.service.MusicService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class SearchController {
    private final BookService bookService;
    private final MusicService musicService;
    private final MeterRegistry meterRegistry;

    @GetMapping("/search")
    @Operation(summary = "Search books and music", description = "Returns a list of books and music based on the input query",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(implementation = SearchResult.class)))
            })
    public ResponseEntity<SearchResult> search(@RequestParam("input") String input) {
        Timer.Sample sample = Timer.start(meterRegistry);
        //fetch sorted list of musics based on input search
        List<Music> musics = musicService.getMusics(input);
        //fetch sorted list of books based on input search
        List<Book> books = bookService.getBooks(input);
        // calculate upstream service response time
        sample.stop(meterRegistry.timer("services.upstream.response.time", "service", "search"));
        SearchResult searchResult = new SearchResult();
        searchResult.setBooks(books);
        searchResult.setMusics(musics);
        return ResponseEntity.ok(searchResult);
    }
}
