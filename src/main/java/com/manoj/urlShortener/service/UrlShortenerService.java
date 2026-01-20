package com.manoj.urlShortener.service;

import com.manoj.urlShortener.model.URLMapping;
import com.manoj.urlShortener.repository.UrlMapppingRepo;

import com.manoj.urlShortener.util.Base62;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private final UrlMapppingRepo urlMapppingRepo;
    private static final int MAX_ATTEMPTS = 10;
    private static final int CODE_LENGTH = 7;

    @Transactional
    public String shortenUrl(String originalUrl) {
        if(originalUrl==null || originalUrl.trim().length()==0){
            throw new IllegalArgumentException("Original url is null or empty");
        }
        String shortCode;
        int  attempts = 0;
        do {
            shortCode = Base62.randomCode(CODE_LENGTH);
            attempts++;
            if(attempts > MAX_ATTEMPTS){
                throw new RuntimeException("Failed to generate shortcode for url");
            }
        }while (urlMapppingRepo.existsByShortCode(shortCode));

        URLMapping urlMapping = URLMapping.builder()
                .OrignalUrl(originalUrl)
                .shortCode(shortCode).build();

        urlMapppingRepo.save(urlMapping);
        return shortCode;
    }

    public String getOriginalUrlAndIncrementClick(String shortCode) {
        URLMapping mapping = urlMapppingRepo.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short code not found" + shortCode));
        Long current = mapping.getClickCount();
        if (current == null) {
            current = 0L;
        }
        mapping.setClickCount(current + 1);
        urlMapppingRepo.save(mapping);
        return mapping.getOrignalUrl();
    }
}
