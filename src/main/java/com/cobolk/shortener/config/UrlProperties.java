package com.cobolk.shortener.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "url")
public class UrlProperties {

    private int expirationDays;
    private int shortCodeLength;
}
