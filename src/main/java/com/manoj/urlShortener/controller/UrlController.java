package com.manoj.urlShortener.controller;

import com.manoj.urlShortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UrlController {

    private final UrlShortenerService service;

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestBody String originalUrl) {
        String shortCode = service.shortenUrl(originalUrl);
        String shortUrl =  "http://localhost:8080/api/" + shortCode;
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode){
        String originalUrl = service.getOriginalUrlAndIncrementClick(shortCode);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);

    }

}
