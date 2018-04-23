package javacore.benchmarktool.benchmark;

import java.net.URL;
import java.time.Duration;

public class BenchmarkSettings {

    private URL targetUrl;
    private int requestCount = 100;
    private int concurrencyLevel = 5;
    private Duration timeout = Duration.ofMillis(100);

    public void setTargetUrl(URL targetUrl) {
        this.targetUrl = targetUrl;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public void setConcurrencyLevel(int concurrencyLevel) {
        this.concurrencyLevel = concurrencyLevel;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public URL getTargetUrl() {
        return targetUrl;
    }

    public int getConcurrencyLevel() {
        return concurrencyLevel;
    }

    public Duration getTimeout() {
        return timeout;
    }

}
