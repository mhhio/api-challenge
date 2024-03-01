package com.example.apichallenge.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class APIConfiguration {
    @Value("${google.books.api.limit:5}")
    private Integer googleBookApiLimit;

    @Value("${apple.musics.api.limit:5}")
    private Integer appleMusicsApiLimit;
}
