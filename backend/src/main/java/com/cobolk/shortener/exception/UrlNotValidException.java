package com.cobolk.shortener.exception;

public class UrlNotValidException extends RuntimeException{

    public UrlNotValidException(String url) {
        super("The provided URL is not valid : " + url);
    }
}
