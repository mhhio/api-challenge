package com.example.apichallenge.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "apple-music", url = "https://itunes.apple.com/search")
public interface AppleMusicClient {
    @GetMapping("/volumes")
    String searchMedia(@RequestParam("term") String query, @RequestParam("attribute") String attribute, @RequestParam("limit") Integer limit, @RequestParam("media") String media);
}
