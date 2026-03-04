package com.cobolk.shortener.repositories;

import com.cobolk.shortener.domain.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UrlRepository extends JpaRepository<Url, UUID> {

    Optional<Url> findUrlByMainUrl(String mainUrl);

    Optional<Url> findUrlByShortCode(String shortCode);
}
