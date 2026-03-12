package com.cobolk.shortener.service;

import com.cobolk.shortener.domain.UrlShortenRequest;
import com.cobolk.shortener.domain.UrlStatsRequest;
import com.cobolk.shortener.domain.entity.Url;

import java.net.URI;

public interface UrlService {
    Url shortenUrl(UrlShortenRequest request);

    URI getRedirectionUri(String shortCode);

    void deleteUrl(String shortCode);

    int getStats(UrlStatsRequest request);
}
