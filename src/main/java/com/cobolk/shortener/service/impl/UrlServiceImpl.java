package com.cobolk.shortener.service.impl;

import com.cobolk.shortener.domain.ShortenUrlRequest;
import com.cobolk.shortener.domain.entity.Url;
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
        String urlToBeParsed = urlRepository.findUrlByShortCode(shortCode)
            .map(Url::getMainUrl)
            .orElse("/");
        return URI.create(urlToBeParsed);
    }

    private String generateShortUrl() {
        return RandomStringUtils.secure().nextAlphanumeric(8);
    }

}
