package com.cobolk.shortener.service;

import com.cobolk.shortener.domain.ShortenUrlRequest;
import com.cobolk.shortener.domain.entity.Url;

import java.net.URI;

public interface UrlService {
    Url shortenUrl(ShortenUrlRequest request);

    URI getRedirectionUri(String shortCode);
}
