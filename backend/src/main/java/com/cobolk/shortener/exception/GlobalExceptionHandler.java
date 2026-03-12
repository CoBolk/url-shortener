package com.cobolk.shortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotValidException.class)
    public ResponseEntity<String> handleUrlNotValid(UrlNotValidException e) {
        return  ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }

    @ExceptionHandler(MainUrlNotFoundException.class)
    public ResponseEntity<String> handleMainUrlNotFound(MainUrlNotFoundException e) {
        return  ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }

    @ExceptionHandler(ShortCodeNotFoundException.class)
    public ResponseEntity<String> handleShortCodeNotFound(ShortCodeNotFoundException e) {
        return  ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }
}
