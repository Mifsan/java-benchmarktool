package javacore.benchmarktool.http;

import org.junit.Assert;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.time.Duration;

import javacore.benchmarktool.http.HttpConnectionImpl;

public class HttpConnectionTest extends Assert {

    @Test(expected = InvalidParameterException.class)
    public void invalidURLThrowException() {
        try {
            HttpConnectionImpl connection = new HttpConnectionImpl(Duration.ZERO);
            connection.open(new URL("ftp://test.ru"));
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void validHTTPURLOpenedSuccessful() {
        try {
            HttpConnectionImpl connection = new HttpConnectionImpl(Duration.ofMillis(10));
            HttpURLConnection conn = connection.open(new URL("http://wwww.google.com"));
            assertNotNull(conn);
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void validHTTPSURLOpenedSuccessful() {
        try {
            HttpConnectionImpl connection = new HttpConnectionImpl(Duration.ofMillis(30));
            HttpURLConnection conn = connection.open(new URL("https://vk.com"));
            assertNotNull(conn);
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }
}