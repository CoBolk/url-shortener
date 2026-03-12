package com.cobolk.shortener.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UrlShortenRequestDto {

    @NotBlank(message = "An URL is required")
    private String mainUrl;
}
