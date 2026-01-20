package com.manoj.urlShortener.repository;

import com.manoj.urlShortener.model.URLMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMapppingRepo extends JpaRepository<URLMapping, Long> {

    Optional<URLMapping> findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);

}
