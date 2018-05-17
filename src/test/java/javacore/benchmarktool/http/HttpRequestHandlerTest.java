package javacore.benchmarktool.http;

import javacore.benchmarktool.benchmark.BenchmarkStatsCollectorImpl;
import javacore.benchmarktool.benchmark.BenchmarkStatsImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.time.Duration;


class MockHttpRequestHandler implements Runnable {

    private final HttpConnection httpConnection;
    private final HttpRequestListener listener;
    private final URL url;
    private boolean isRequestSuccess = false;
    private boolean isRequestTimeout = false;
    private boolean isRequestError = false;
    private InputStream response;

    MockHttpRequestHandler(URL url, HttpConnection httpConnection, HttpRequestListener listener) {
        this.url = url;
        this.httpConnection = httpConnection;
        this.listener = listener;
    }

    public void ResetIndicators() {
        isRequestSuccess = false;
        isRequestTimeout = false;
        isRequestError = false;
    }

    public void run() {

        ResetIndicators();

        HttpURLConnection conn = null;

        try {
            isRequestSuccess = true;
            conn = httpConnection.open(url);
            readResponse(conn);
        } catch (SocketTimeoutException e) {
            isRequestTimeout = true;
        } catch (IOException e) {
            isRequestError = true;
        }
    }

    public boolean IsRequestSuccess() { return isRequestSuccess; }
    public boolean IsRequestError() { return isRequestError; }
//    public boolean IsRequestTimeout() { return isRequestTimeout; }
    public int GetResponseLenght() {
        return response != null ? response.toString().length() : 0;
    }

    private void readResponse(HttpURLConnection conn) throws IOException {
        response = conn.getInputStream();
    }
}

public class HttpRequestHandlerTest extends Assert {

    private HttpRequestListener listener;
    private HttpConnection httpConnect;
    private MockHttpRequestHandler mockHttpRequestHandler;

    @Before
    public void setup() {
        try {
            this.listener = new BenchmarkStatsCollectorImpl();
            this.httpConnect = new HttpConnectionImpl(Duration.ofMillis(1000));
        } catch (Exception ex) {
            fail("error init mock depincy classes");
        }
    }

    @Test
    public void validHostHandlerResponseSuccess() {
        try {
            this.mockHttpRequestHandler = new MockHttpRequestHandler(
                    new URL("https://vk.com"),
                    this.httpConnect,
                    this.listener);

            this.mockHttpRequestHandler.run();

            assertTrue(this.mockHttpRequestHandler.IsRequestSuccess());
            assertTrue(this.mockHttpRequestHandler.GetResponseLenght() > 0);
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidHostHandlerResponseError() {
        try {
            this.mockHttpRequestHandler = new MockHttpRequestHandler(
                    new URL("https://vksdfsdfe3fefs.com"),
                    this.httpConnect,
                    this.listener);

            this.mockHttpRequestHandler.run();

            assertTrue(this.mockHttpRequestHandler.IsRequestError());
            assertTrue(this.mockHttpRequestHandler.GetResponseLenght() == 0);
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void notworkingHostHandlerResponseError() {
        try {
            this.mockHttpRequestHandler = new MockHttpRequestHandler(
                    new URL("https://yandex.ru/images/wewewewe"),
                    this.httpConnect,
                    this.listener);

            this.mockHttpRequestHandler.run();
            assertTrue(this.mockHttpRequestHandler.IsRequestError());
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }

}