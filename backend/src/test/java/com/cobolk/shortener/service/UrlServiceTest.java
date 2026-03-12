package com.cobolk.shortener.service;

import com.cobolk.shortener.config.UrlProperties;
import com.cobolk.shortener.domain.UrlShortenRequest;
import com.cobolk.shortener.domain.UrlStatsRequest;
import com.cobolk.shortener.domain.entity.Url;
import com.cobolk.shortener.exception.MainUrlNotFoundException;
import com.cobolk.shortener.exception.ShortCodeNotFoundException;
import com.cobolk.shortener.exception.UrlNotValidException;
import com.cobolk.shortener.repositories.UrlRepository;
import com.cobolk.shortener.service.impl.UrlServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @InjectMocks
    private UrlServiceImpl urlService;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UrlProperties urlProperties;

    @Test
    void shortenUrl_shouldThrowUrlNotValidException_whenUrlIsInvalid() {

        UrlShortenRequest urlShortenRequest = new UrlShortenRequest();
        urlShortenRequest.setUrl("not-a-url-123");

        assertThatThrownBy(() ->
            urlService.shortenUrl(urlShortenRequest))
            .isInstanceOf(UrlNotValidException.class);

    }

    @Test
    void shortenUrl_shouldCreateShortCode_whenUrlIsValid() {

        UrlShortenRequest urlShortenRequest = new UrlShortenRequest();
        urlShortenRequest.setUrl("https://google.com");

        when(urlRepository.findUrlByMainUrl("https://google.com"))
            .thenReturn(Optional.empty());

        when(urlProperties.getShortCodeLength()).thenReturn(8);

        when(urlRepository.save(any(Url.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        Url url = urlService.shortenUrl(urlShortenRequest);

        assertThat(url.getShortCode()).isNotNull();
    }

    @Test
    void shortenUrl_shouldProvideUri_whenShortCodeIsValid() {

        String shortCode = "test123";
        String expectedUrl = "https://google.com";

        Url testUrl = Url.builder()
            .mainUrl("https://google.com")
            .shortCode("test123")
            .clickedCount(0)
            .build();

        when(urlRepository.findUrlByShortCode(shortCode))
            .thenReturn(Optional.of(testUrl));


        URI uriTest = urlService.getRedirectionUri(shortCode);

        assertThat(uriTest.toString()).isEqualTo(expectedUrl);
    }

    @Test
    void getRedirectionUri_shouldIncrementClickCount_whenShortCodeIsValid() {

        String shortCode = "test123";

        Url testUrl = Url.builder()
            .mainUrl("https://google.com")
            .shortCode("test123")
            .clickedCount(0)
            .build();

        when(urlRepository.findUrlByShortCode(shortCode))
            .thenReturn(Optional.of(testUrl));

        urlService.getRedirectionUri(shortCode);

        verify(urlRepository).save(argThat(savedUrl ->
            savedUrl.getClickedCount() == 1
        ));
    }

    @Test
    void shortenUrl_shouldThrowShortCodeNotFoundException_whenShortCodeNotFound() {

        String shortCode = "test123";

        assertThatThrownBy(() ->
            urlService.getRedirectionUri(shortCode))
            .isInstanceOf(ShortCodeNotFoundException.class);
    }

    @Test
    void deleteUrl_shouldDeleteUrl_whenShortCodeIsValid() {

        String shortCode = "test123";

        Url testUrl = Url.builder()
            .mainUrl("https://google.com")
            .shortCode("test123")
            .build();

        when(urlRepository.findUrlByShortCode(shortCode))
            .thenReturn(Optional.of(testUrl));

        urlService.deleteUrl(shortCode);

        verify(urlRepository).delete(testUrl);
    }

    @Test
    void deleteUrl_shouldThrowException_whenShortCodeIsInvalid() {

        String shortCode = "test123";

        when(urlRepository.findUrlByShortCode(shortCode))
            .thenReturn(Optional.empty());

        assertThatThrownBy(() -> urlService.deleteUrl(shortCode))
            .isInstanceOf(ShortCodeNotFoundException.class);
    }

    @Test
    void getStats_shouldProvideClickCount_whenMainUrlIsValid() {

        UrlStatsRequest urlStatsRequest = new UrlStatsRequest("https://google.com");

        String mainUrl = urlStatsRequest.getMainUrl();

        Url testUrl = Url.builder()
            .mainUrl("https://google.com")
            .shortCode("test123")
            .clickedCount(2)
            .build();

        when(urlRepository.findUrlByMainUrl(mainUrl))
            .thenReturn(Optional.of(testUrl));

        int statsTest = urlService.getStats(urlStatsRequest);

        assertThat(statsTest).isEqualTo(testUrl.getClickedCount());
    }

    @Test
    void getStats_shouldThrowMainUrlNotFoundException_whenMainUrlNotFound() {

        UrlStatsRequest urlStatsRequest = new UrlStatsRequest("https://google.com");

        assertThatThrownBy(() ->
            urlService.getStats(urlStatsRequest))
            .isInstanceOf(MainUrlNotFoundException.class);
    }

}
