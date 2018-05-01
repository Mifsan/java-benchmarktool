package javacore.benchmarktool.http;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;


class MockHttpRequestListener implements HttpRequestListener {

    private Duration timeSpent;
    private long bytesCount = 0;
    private int httpStatusCode = 0;
    private boolean isRequestError = false;
    private boolean isRequestTimeout = false;

    final Duration GetTimeSpent() {
        return this.timeSpent;
    }

    final long GetBytesCount() {
        return this.bytesCount;
    }

    final int GetHttpStatusCode() {
        return this.httpStatusCode;
    }

    final boolean IsRequestError() {
        return this.isRequestError;
    }

    final boolean IsRequestTimeout() {
        return this.isRequestTimeout;
    }

    final boolean IsRequestSuccess() {
        return !this.isRequestTimeout && !this.isRequestError;
    }

    @Override
    public void onRequestComplete(Duration timeSpent, long transmittedByteCount, int httpStatusCode) {
        synchronized (this) {
            isRequestError = false;
            isRequestTimeout = false;
            this.timeSpent = timeSpent;
            this.bytesCount = transmittedByteCount;
            this.httpStatusCode = httpStatusCode;
        }
    }

    @Override
    public void onRequestError(RuntimeException ex) {
        isRequestError = true;
    }

    @Override
    public void onRequestTimeout() {
        isRequestTimeout = true;
    }
}

public class HttpRequestListenerTest extends Assert {

    private MockHttpRequestListener mockListener;
    private HttpConnection httpConnect;
    private HttpRequestHandler httpRequestHandler;

    @Before
    public void setup() {
        try {
            this.mockListener = new MockHttpRequestListener();
            this.httpConnect = new HttpConnectionImpl(Duration.ofMillis(1000));
        } catch (Exception ex) {
            fail("error init mock depincy classes");
        }
    }

    @Test
    public void validHostResponseSuccess() {
        try {
            this.httpRequestHandler = new HttpRequestHandler(
                    new URL("https://vk.com"),
                    this.httpConnect,
                    this.mockListener);

            this.httpRequestHandler.run();

            assertTrue(this.mockListener.IsRequestSuccess());
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void invalidHostResponseError() {
        try {
            this.httpRequestHandler = new HttpRequestHandler(
                    new URL("https://vksdfcsecsecsdcsd.com"),
                    this.httpConnect,
                    this.mockListener);

            this.httpRequestHandler.run();

            assertTrue(this.mockListener.IsRequestError());
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void validHostSendNormalResponse() {
        try {
            this.httpRequestHandler = new HttpRequestHandler(
                    new URL("https://vk.com"),
                    this.httpConnect,
                    this.mockListener);

            this.httpRequestHandler.run();

            assertTrue(this.mockListener.GetBytesCount() > 0);
            assertEquals(this.mockListener.GetHttpStatusCode(), 200);
            assertTrue(this.mockListener.GetTimeSpent() != Duration.ofMillis(0));

        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }

}