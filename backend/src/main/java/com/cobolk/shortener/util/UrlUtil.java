package com.cobolk.shortener.util;

import java.net.URL;

public class UrlUtil {

    public static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
