package com.cobolk.shortener.exception;

public class MainUrlNotFoundException extends RuntimeException {

    public MainUrlNotFoundException(String mainUrl) {
        super("The provided mainUrl was not found : " + mainUrl);
    }
}
