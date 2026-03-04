package com.cobolk.shortener.service.impl;

import com.cobolk.shortener.domain.ShortenUrlRequest;
import com.cobolk.shortener.domain.entity.Url;
import com.cobolk.shortener.exception.ShortCodeNotFoundException;
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

    @Override
    public Url shortenUrl(ShortenUrlRequest request) {
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

    private String generateShortUrl() {
        return RandomStringUtils.secure().nextAlphanumeric(8);
    }
    
    private void incrementClickCount(Url url) {
        url.setClickedCount(url.getClickedCount() + 1);
        urlRepository.save(url);
    }

}
