package com.cobolk.shortener.mapper;

import com.cobolk.shortener.domain.ShortenUrlRequest;
import com.cobolk.shortener.domain.dto.ShortenUrlRequestDto;
import com.cobolk.shortener.domain.dto.ShortenUrlResponseDto;
import com.cobolk.shortener.domain.entity.Url;

public class UrlMapper {

    public static ShortenUrlRequest toRequest(ShortenUrlRequestDto dto) {
        ShortenUrlRequest request = new ShortenUrlRequest();
        request.setUrl(dto.getUrl());
        return request;
    }

    public static ShortenUrlResponseDto toDto(Url url) {
        ShortenUrlResponseDto shortenUrlResponseDto = new ShortenUrlResponseDto();
        shortenUrlResponseDto.setShortCode(url.getShortCode());
        return shortenUrlResponseDto;
    }
}
