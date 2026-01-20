package com.manoj.urlShortener.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity(name="UrlMapping")
@Table(name="url_mappings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class URLMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    @Column(nullable = false, columnDefinition = "Text")
    private String OrignalUrl;

    @Column(nullable = false, unique = true, length = 10)
    private String shortCode;

    private Long clickCount = 0L;
    private Instant createdAt = Instant.now();
    private Instant expiresAt;
}
