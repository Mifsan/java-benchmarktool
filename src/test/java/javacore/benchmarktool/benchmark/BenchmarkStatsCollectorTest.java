package javacore.benchmarktool.benchmark;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import javacore.benchmarktool.http.HttpConnection;
import javacore.benchmarktool.http.HttpConnectionImpl;
import javacore.benchmarktool.http.HttpRequestHandler;
import javacore.benchmarktool.benchmark.BenchmarkStats;
import javacore.benchmarktool.benchmark.BenchmarkStatsImpl;
import javacore.benchmarktool.benchmark.BenchmarkStatsCollector;

class MockBenchmarkStatsCollector extends BenchmarkStatsCollectorImpl{
    private BenchmarkStatsImpl benchmarkStatsImpl = new BenchmarkStatsImpl();
    private boolean isRequestError = false;
    private boolean isRequestTimeout = false;

    final boolean IsRequestError() {
        return this.isRequestError;
    }

    final boolean IsRequestTimeout() {
        return this.isRequestTimeout;
    }

    final boolean IsRequestSuccess() {
        return !this.isRequestTimeout && !this.isRequestError;
    }

    public BenchmarkStats getStats() {
        return benchmarkStatsImpl;
    }

    public void onRequestComplete(Duration timeSpent, long transmittedByteCount, int httpStatusCode) {
        synchronized (this) {
            isRequestError = false;
            isRequestTimeout = false;
        }
    }

    public void onRequestError(RuntimeException ex) {
        isRequestError = true;
    }

    public void onRequestTimeout() {
        isRequestTimeout = true;
    }
}

public class BenchmarkStatsCollectorTest extends Assert {

    private MockBenchmarkStatsCollector mockCollector;
    private HttpConnection httpConnect;
    private HttpRequestHandler httpRequestHandler;

    @Before
    public void setup() {
        try {
            this.mockCollector = new MockBenchmarkStatsCollector();
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
                    this.mockCollector);

            this.httpRequestHandler.run();

            assertTrue(this.mockCollector.IsRequestSuccess());
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
                    this.mockCollector);

            this.httpRequestHandler.run();

            assertTrue(this.mockCollector.IsRequestError());
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
                    this.mockCollector);

            this.httpRequestHandler.run();

            BenchmarkStats receivedStat = this.mockCollector.getStats();
            assertTrue(mockCollector.IsRequestSuccess());
            assertEquals(receivedStat.getFailedRequestsCount(), 0);
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }

}