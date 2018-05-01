package javacore.benchmarktool.benchmark;

import java.net.HttpURLConnection;
import java.time.Duration;

public class BenchmarkStatsCollectorImpl implements BenchmarkStatsCollector {
    private BenchmarkStatsImpl benchmarkStatsImpl = new BenchmarkStatsImpl();
    private RuntimeException lastException;

    @Override
    public BenchmarkStats getStats() throws RuntimeException {
        if (lastException != null) {
            throw lastException;
        }
        return benchmarkStatsImpl;
    }

    @Override
    public void onRequestComplete(Duration timeSpent, long transmittedByteCount, int httpStatusCode) {
        final boolean succeed =
                (httpStatusCode >= HttpURLConnection.HTTP_OK && httpStatusCode < HttpURLConnection.HTTP_BAD_REQUEST);
        synchronized (this) {
            this.benchmarkStatsImpl.addRequest(succeed, transmittedByteCount, timeSpent);
        }
    }

    @Override
    public void onRequestTimeout() {
        benchmarkStatsImpl.addKilledRequest();
    }

    @Override
    public void onRequestError(RuntimeException ex) {
        synchronized (this) {
            this.lastException = ex;
        }
    }

}
