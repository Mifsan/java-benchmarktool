package javacore.benchmarktool.benchmark;

import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BenchmarkSettingsTest extends Assert {

    @Test
    public void emptySettingsReturnDefaultValues() {
        BenchmarkSettingsImpl benchmarkSettingsImpl = new BenchmarkSettingsImpl();

        assertEquals(benchmarkSettingsImpl.getRequestCount(), 100);
        assertEquals(benchmarkSettingsImpl.getConcurrencyLevel(), 5);
        assertEquals(benchmarkSettingsImpl.getTimeout(), Duration.ofMillis(100));
    }

    @Test
    public void filledSettingsReturnedInGetters() {
        BenchmarkSettingsImpl benchmarkSettingsImpl = new BenchmarkSettingsImpl();

        benchmarkSettingsImpl.setConcurrencyLevel(10);
        try {
            benchmarkSettingsImpl.setTargetUrl(new URL("http://test.ru/"));
            assertEquals(benchmarkSettingsImpl.getTargetUrl(), new URL("http://test.ru/"));
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
        benchmarkSettingsImpl.setRequestCount(20);
        benchmarkSettingsImpl.setTimeout(Duration.ofMillis(500));

        assertEquals(benchmarkSettingsImpl.getRequestCount(), 20);
        assertEquals(benchmarkSettingsImpl.getConcurrencyLevel(), 10);
        assertEquals(benchmarkSettingsImpl.getTimeout(), Duration.ofMillis(500));
    }
}
