package com.cobolk.shortener.service.impl;

import com.cobolk.shortener.config.UrlProperties;
import com.cobolk.shortener.domain.UrlShortenRequest;
import com.cobolk.shortener.domain.UrlStatsRequest;
import com.cobolk.shortener.domain.entity.Url;
import com.cobolk.shortener.exception.ShortCodeNotFoundException;
import com.cobolk.shortener.exception.MainUrlNotFoundException;
import com.cobolk.shortener.exception.UrlNotValidException;
import com.cobolk.shortener.repositories.UrlRepository;
import com.cobolk.shortener.service.UrlService;
import com.cobolk.shortener.util.UrlUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UrlProperties urlProperties;

    @Override
    public Url shortenUrl(UrlShortenRequest request) {
        String url = request.getUrl();
        if (!UrlUtil.isValid(url)) throw new UrlNotValidException(url);

        return urlRepository.findUrlByMainUrl(url)
            .orElseGet(() -> {
                Url urlEntity = new Url();
                urlEntity.setMainUrl(url);
                urlEntity.setShortCode(generateShortUrl());
                return urlRepository.save(urlEntity);

        });
    }

    @Override
    public URI getRedirectionUri(String shortCode) {
        Url url = urlRepository.findUrlByShortCode(shortCode)
            .orElseThrow(() -> new ShortCodeNotFoundException(shortCode));

        incrementClickCount(url);

        return URI.create(url.getMainUrl());
    }

    @Override
    public void deleteUrl(String shortCode) {
        Url url = urlRepository.findUrlByShortCode(shortCode)
            .orElseThrow(() -> new ShortCodeNotFoundException(shortCode));

        urlRepository.delete(url);
    }

    @Override
    public int getStats(UrlStatsRequest request) {
        String mainUrl = request.getMainUrl();
        Url url = urlRepository.findUrlByMainUrl(mainUrl)
            .orElseThrow(() -> new MainUrlNotFoundException(mainUrl));

        return url.getClickedCount();
    }

    private String generateShortUrl() {
        return RandomStringUtils.secure().nextAlphanumeric(urlProperties.getShortCodeLength());
    }
    
    private void incrementClickCount(Url url) {
        url.setClickedCount(url.getClickedCount() + 1);
        urlRepository.save(url);
    }

}
