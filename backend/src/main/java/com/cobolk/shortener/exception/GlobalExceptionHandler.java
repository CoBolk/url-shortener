package com.cobolk.shortener.exception;

import com.cobolk.shortener.domain.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotValidException.class)
    public ResponseEntity<ErrorDto> handleUrlNotValid(UrlNotValidException e) {
        return  ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(MainUrlNotFoundException.class)
    public ResponseEntity<ErrorDto> handleMainUrlNotFound(MainUrlNotFoundException e) {
        return  ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build()
            );
    }

    @ExceptionHandler(ShortCodeNotFoundException.class)
    public ResponseEntity<ErrorDto> handleShortCodeNotFound(ShortCodeNotFoundException e) {
        return  ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build());
    }
}
