package com.cobolk.shortener.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorDto {
    private int status;
    private String message;
}
