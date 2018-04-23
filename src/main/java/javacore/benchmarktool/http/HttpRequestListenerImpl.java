package javacore.benchmarktool.http;

import javacore.benchmarktool.benchmark.BenchmarkStats;

import java.net.HttpURLConnection;
import java.time.Duration;

public class HttpRequestListenerImpl implements HttpRequestListener {

    private BenchmarkStats benchmarkStats;
    private Exception lastException;

    public HttpRequestListenerImpl(BenchmarkStats benchmarkStats) {
        this.benchmarkStats = benchmarkStats;
    }

    @Override
    public void onRequestComplete(Duration timeSpent, long transmittedByteCount, int httpStatusCode) {
        final boolean succeed =
                (httpStatusCode >= HttpURLConnection.HTTP_OK && httpStatusCode < HttpURLConnection.HTTP_BAD_REQUEST);
        synchronized (this) {
            System.out.println("succeed");
            this.benchmarkStats.addRequest(succeed, transmittedByteCount, timeSpent);
        }
    }

    @Override
    public void onRequestTimeout() {
        //System.out.println("timeout");
        benchmarkStats.addKilledRequest();
    }

    @Override
    public void onRequestError(RuntimeException ex) {
        synchronized (this) {
            //System.out.println("req err");
            this.lastException = ex;
        }
    }

    @Override
    public void setTotalDuration(Duration duration) {
        System.out.println("total duration"); //Это лишнее, можно высчитать прямо в стате
        //this.benchmarkStats.setRequestsTotalDuration(duration);
    }
}
