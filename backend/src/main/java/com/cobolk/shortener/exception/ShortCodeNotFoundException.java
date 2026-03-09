package com.cobolk.shortener.exception;

public class ShortCodeNotFoundException extends RuntimeException{

    public ShortCodeNotFoundException(String shortCode) {
        super("The provided ShortCode was not found : " + shortCode);
    }
}
