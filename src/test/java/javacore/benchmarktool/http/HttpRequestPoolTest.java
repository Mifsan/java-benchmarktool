package javacore.benchmarktool.http;

import javacore.benchmarktool.benchmark.BenchmarkStatsCollectorImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class MockHttpRequestPool {

    private final HttpRequestListener reqListener;
    private final HttpConnection httpConnect;
    private final ExecutorService executorService;
    private int requestsCount = 0;

    public MockHttpRequestPool(HttpConnection httpConnect, HttpRequestListener reqListener, int concurrency) {
        this.httpConnect = httpConnect;
        this.reqListener = reqListener;
        this.executorService = Executors.newFixedThreadPool(concurrency);
    }

    public void runRequests(URL url, int requestCount) {
        for (int i = 0; i < requestCount; ++i) {
            HttpRequestHandler task = new HttpRequestHandler(url, this.httpConnect, this.reqListener);
            this.executorService.execute(task);
            requestsCount++;
        }
        await();
    }

    public int GetRequestsCount() {
        return this.requestsCount;
    }

    private void await() {
        this.executorService.shutdown();
        try {
            this.executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            this.executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

public class HttpRequestPoolTest extends Assert {

    private HttpConnection httpConnect;
    private HttpRequestListener httpListener;

    @Before
    public void setup() {
        try {
            this.httpConnect = new HttpConnectionImpl(Duration.ofMillis(1000));
            this.httpListener = new BenchmarkStatsCollectorImpl();
        } catch (Exception ex) {
            fail("error init mock depincy classes");
        }
    }

    @Test
    public void targetRequestCountAllRun() {
        MockHttpRequestPool mockRequestPool = new MockHttpRequestPool(this.httpConnect, this.httpListener, 10);
        try {
            mockRequestPool.runRequests(new URL("http://vk.com/"), 20);
            assertEquals(mockRequestPool.GetRequestsCount(), 20);
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
    }
}