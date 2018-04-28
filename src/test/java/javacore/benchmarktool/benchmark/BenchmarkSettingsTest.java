package javacore.benchmarktool.benchmark;

import javacore.benchmarktool.http.HttpConnectionImpl;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BenchmarkSettingsTest extends Assert {

    @Test
    public void emptySettingsReturnDefaultValues() {
        BenchmarkSettings benchmarkSettings = new BenchmarkSettings();

        assertEquals(benchmarkSettings.getRequestCount(), 100);
        assertEquals(benchmarkSettings.getConcurrencyLevel(), 5);
        assertEquals(benchmarkSettings.getTimeout(), Duration.ofMillis(100));
    }

    @Test
    public void filledSettingsReturnedInGetters() {
        BenchmarkSettings benchmarkSettings = new BenchmarkSettings();

        benchmarkSettings.setConcurrencyLevel(10);
        try {
            benchmarkSettings.setTargetUrl(new URL("http://test.ru/"));
            assertEquals(benchmarkSettings.getTargetUrl(), new URL("http://test.ru/"));
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
        benchmarkSettings.setRequestCount(20);
        benchmarkSettings.setTimeout(Duration.ofMillis(500));

        assertEquals(benchmarkSettings.getRequestCount(), 20);
        assertEquals(benchmarkSettings.getConcurrencyLevel(), 10);
        assertEquals(benchmarkSettings.getTimeout(), Duration.ofMillis(500));
    }
}
