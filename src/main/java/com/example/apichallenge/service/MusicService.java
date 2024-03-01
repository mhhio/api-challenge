package com.example.apichallenge.service;

import com.example.apichallenge.client.AppleMusicClient;
import com.example.apichallenge.config.APIConfiguration;
import com.example.apichallenge.constant.Media;
import com.example.apichallenge.model.Book;
import com.example.apichallenge.model.Music;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MusicService {

    private final AppleMusicClient appleMusicClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final APIConfiguration apiConfiguration;

    @Cacheable("musics")
    @CircuitBreaker(name = "musicServiceCircuitBreaker", fallbackMethod = "fallbackGetMusics")
    public List<Music> getMusics(String query) {
        List<Music> musics = new ArrayList<>();
        String response = appleMusicClient.searchMedia(query, "songTerm", apiConfiguration.getAppleMusicsApiLimit(), Media.MUSIC.value());
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            log.error("invalid json response {} with err:",response,e);
            //todo need to handle error
            throw new RuntimeException(e);
        }
        if (rootNode.has("results")) {
            for (JsonNode result : rootNode.path("results")) {
                Music music = new Music();
                music.setKind(result.path("kind").asText());
                music.setArtistId(result.path("artistId").asInt());
                music.setTrackId(result.path("trackId").asInt());
                music.setArtistName(result.path("artistName").asText());
                music.setCollectionName(result.path("collectionName").asText());
                music.setTrackName(result.path("trackName").asText());
                music.setCollectionCensoredName(result.path("collectionCensoredName").asText());
                music.setTrackCensoredName(result.path("trackCensoredName").asText());
                music.setReleaseDate(convertDate(result.path("releaseDate").asText()));
                musics.add(music);

            }
        }

        //sort music by track name
        musics.sort(Comparator.comparing(Music::getTrackName));
        return musics;
    }

    private String convertDate(String releaseDate) {
        LocalDate date = ZonedDateTime.parse(releaseDate).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    private List<Book> fallbackGetMusics(String query, Throwable t) {
        //todo add search in cache
        return new ArrayList<>();
    }
}
