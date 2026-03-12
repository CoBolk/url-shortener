package com.cobolk.shortener.mapper;

import com.cobolk.shortener.config.UrlProperties;
import com.cobolk.shortener.domain.UrlShortenRequest;
import com.cobolk.shortener.domain.UrlStatsRequest;
import com.cobolk.shortener.domain.dto.UrlShortenRequestDto;
import com.cobolk.shortener.domain.dto.UrlShortenResponseDto;
import com.cobolk.shortener.domain.dto.UrlStatsRequestDto;
import com.cobolk.shortener.domain.dto.UrlStatsResponseDto;
import com.cobolk.shortener.domain.entity.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UrlMapper {

    private final UrlProperties urlProperties;

    public UrlShortenRequest toRequest(UrlShortenRequestDto dto) {
        UrlShortenRequest request = new UrlShortenRequest();
        request.setUrl(dto.getUrl());
        return request;
    }

    public UrlShortenResponseDto toDto(Url url) {
        UrlShortenResponseDto urlShortenResponseDto = new UrlShortenResponseDto();
        urlShortenResponseDto.setUrlShortCode(urlProperties.getDomainName() + "/" + url.getShortCode());
        return urlShortenResponseDto;
    }

    public UrlStatsRequest toRequest(UrlStatsRequestDto dto) {
        UrlStatsRequest request = new UrlStatsRequest();
        request.setMainUrl(dto.getMainUrl());
        return request;
    }

    public UrlStatsResponseDto toDto(int clickCount) {
        UrlStatsResponseDto urlStatsResponseDto = new UrlStatsResponseDto();
        urlStatsResponseDto.setClickCount(clickCount);
        return urlStatsResponseDto;
    }
}
