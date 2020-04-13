package com.jaysonleon.shuzzle.util;

public class UrlUtil {

    public static String convertImageUrl(String url){
        return url.replace("amp;", "");
    }
}
