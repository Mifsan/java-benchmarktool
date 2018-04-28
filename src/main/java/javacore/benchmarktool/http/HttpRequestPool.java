package javacore.benchmarktool.http;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpRequestPool {
    
    private final HttpRequestListener reqListener;
    private final HttpConnection httpConnect;
    private final ExecutorService executorService;

    public HttpRequestPool(HttpConnection httpConnect, HttpRequestListener reqListener, int concurrency) {
        this.httpConnect = httpConnect;
        this.reqListener = reqListener;
        this.executorService = Executors.newFixedThreadPool(concurrency);
    }

    public void runRequests(URL url, int requestCount) {
        for (int i = 0; i < requestCount; ++i) {
            HttpRequestHandler task = new HttpRequestHandler(url, this.httpConnect, this.reqListener);
            this.executorService.execute(task);
        }
        await();
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
