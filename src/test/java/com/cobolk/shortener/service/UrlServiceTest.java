package com.cobolk.shortener.service;

import com.cobolk.shortener.domain.ShortenUrlRequest;
import com.cobolk.shortener.domain.entity.Url;
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

    @Test
    void shortenUrl_shouldThrowUrlNotValidException_whenUrlIsInvalid() {

        ShortenUrlRequest shortenUrlRequest = new ShortenUrlRequest();
        shortenUrlRequest.setUrl("NOT-A-URL");

        assertThatThrownBy(() ->
            urlService.shortenUrl(shortenUrlRequest))
            .isInstanceOf(UrlNotValidException.class);

    }

    @Test
    void shortenUrl_shouldCreateShortCode_whenUrlIsValid() {

        ShortenUrlRequest shortenUrlRequest = new ShortenUrlRequest();
        shortenUrlRequest.setUrl("https://google.com");

        when(urlRepository.findUrlByMainUrl("https://google.com"))
            .thenReturn(Optional.empty());

        when(urlRepository.save(any(Url.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        Url url = urlService.shortenUrl(shortenUrlRequest);

        assertThat(url.getShortCode()).isNotNull();
    }

    @Test
    void shortenUrl_shouldProvideUri_whenShortCodeIsValid() {

        String shortCode = "test123";
        String expectedUrl = "https://google.com";

        Url testUrl = Url.builder()
            .mainUrl("https://google.com")
            .shortCode("test123")
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
            .clickedCount(0) // On part de 0
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

}
