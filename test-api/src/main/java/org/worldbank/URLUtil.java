package org.worldbank;

/**
 * Created by Taylan on 28.05.2016.
 */
public class URLUtil {

    public static String https(String urlWithHttp) {
        return urlWithHttp.replace("http", "https");
    }

    public static boolean matches(String url1, String url2) {
        String url1Cropped = url1.replace("https", "").replace("http", "").toLowerCase();
        String url2Cropped = url2.replace("https", "").replace("http", "").toLowerCase();

        return url1Cropped.equals(url2Cropped);
    }

    public static String trimForUrl(String text) {
        return text.replace(" ", "-").replace(",", "").replace(".", "").replace("(", "").replace(")", "").replace("'", "");
    }
}
