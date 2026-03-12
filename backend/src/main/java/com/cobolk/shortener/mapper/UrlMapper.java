package com.cobolk.shortener.mapper;

import com.cobolk.shortener.domain.UrlShortenRequest;
import com.cobolk.shortener.domain.UrlStatsRequest;
import com.cobolk.shortener.domain.dto.UrlShortenRequestDto;
import com.cobolk.shortener.domain.dto.UrlShortenResponseDto;
import com.cobolk.shortener.domain.dto.UrlStatsRequestDto;
import com.cobolk.shortener.domain.dto.UrlStatsResponseDto;
import com.cobolk.shortener.domain.entity.Url;

public class UrlMapper {

    public static UrlShortenRequest toRequest(UrlShortenRequestDto dto) {
        UrlShortenRequest request = new UrlShortenRequest();
        request.setUrl(dto.getUrl());
        return request;
    }

    public static UrlShortenResponseDto toDto(Url url) {
        UrlShortenResponseDto urlShortenResponseDto = new UrlShortenResponseDto();
        urlShortenResponseDto.setShortCode(url.getShortCode());
        return urlShortenResponseDto;
    }

    public static UrlStatsRequest toRequest(UrlStatsRequestDto dto) {
        UrlStatsRequest request = new UrlStatsRequest();
        request.setMainUrl(dto.getMainUrl());
        return request;
    }

    public static UrlStatsResponseDto toDto(int clickCount) {
        UrlStatsResponseDto urlStatsResponseDto = new UrlStatsResponseDto();
        urlStatsResponseDto.setClickCount(clickCount);
        return urlStatsResponseDto;
    }
}
