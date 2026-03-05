package com.cobolk.shortener.controller;

import com.cobolk.shortener.domain.ShortenUrlRequest;
import com.cobolk.shortener.domain.dto.ShortenUrlRequestDto;
import com.cobolk.shortener.domain.dto.ShortenUrlResponseDto;
import com.cobolk.shortener.domain.entity.Url;
import com.cobolk.shortener.mapper.UrlMapper;
import com.cobolk.shortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UrlController {

    private final UrlService urlService;

    @PostMapping(path = "/shorten")
    public ResponseEntity<ShortenUrlResponseDto> shortenUrl(@RequestBody ShortenUrlRequestDto dto)
    {
        ShortenUrlRequest shortenUrlRequest = UrlMapper.toRequest(dto);
        Url url = urlService.shortenUrl(shortenUrlRequest);
        ShortenUrlResponseDto shortenUrlResponseDto = UrlMapper.toDto(url);

        return ResponseEntity.ok(shortenUrlResponseDto);
    }

    @GetMapping(path = "/{shortCode}")
    public ResponseEntity<Void> getRedirectionUrl(@PathVariable String shortCode) {
        return ResponseEntity.status(HttpStatus.FOUND.value())
            .location(urlService.getRedirectionUri(shortCode))
            .build();

    }

    @DeleteMapping(path = "/{shortCode}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortCode) {
        urlService.deleteUrl(shortCode);
        return ResponseEntity.ok().build();
    }
}
