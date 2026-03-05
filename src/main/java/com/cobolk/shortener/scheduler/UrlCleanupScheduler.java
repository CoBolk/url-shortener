package com.cobolk.shortener.scheduler;

import com.cobolk.shortener.config.UrlProperties;
import com.cobolk.shortener.repositories.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UrlCleanupScheduler {

    private final UrlRepository urlRepository;
    private final UrlProperties urlProperties;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredUrls() {
        LocalDateTime expirationDate = LocalDateTime.now().minusDays(urlProperties.getExpirationDays());
        urlRepository.deleteUrlByCreatedAtBefore(expirationDate);
    }

}
