package com.cobolk.shortener.controller;

import com.cobolk.shortener.domain.UrlShortenRequest;
import com.cobolk.shortener.domain.UrlStatsRequest;
import com.cobolk.shortener.domain.dto.UrlShortenRequestDto;
import com.cobolk.shortener.domain.dto.UrlShortenResponseDto;
import com.cobolk.shortener.domain.dto.UrlStatsRequestDto;
import com.cobolk.shortener.domain.dto.UrlStatsResponseDto;
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

    @PostMapping(path = "/api/shorten")
    public ResponseEntity<UrlShortenResponseDto> shortenUrl(@RequestBody UrlShortenRequestDto dto)
    {
        UrlShortenRequest urlShortenRequest = UrlMapper.toRequest(dto);
        Url url = urlService.shortenUrl(urlShortenRequest);
        UrlShortenResponseDto urlShortenResponseDto = UrlMapper.toDto(url);

        return ResponseEntity.ok(urlShortenResponseDto);
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

    @GetMapping(path = "/stats")
    public ResponseEntity<UrlStatsResponseDto> getStats(@RequestBody UrlStatsRequestDto dto) {
        UrlStatsRequest urlStatsRequest = UrlMapper.toRequest(dto);
        int clickedCount = urlService.getStats(urlStatsRequest);

        UrlStatsResponseDto urlStatsResponseDto = UrlMapper.toDto(clickedCount);
        return ResponseEntity.ok(urlStatsResponseDto);
    }
}
