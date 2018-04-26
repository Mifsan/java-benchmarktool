package javacore.benchmarktool.benchmark;

import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BenchmarkStatTest extends Assert {

    @Test
    public void emptyStatsReturnDefaultZeroValues() {
        BenchmarkStats benchmarkStats = new BenchmarkStats();

        assertEquals((long) benchmarkStats.getRequestsPerSecond(), 0);
        assertEquals(benchmarkStats.getAverageRequestDuration(), Duration.ZERO);
        assertEquals(benchmarkStats.getRequestsTotalDuration(), Duration.ZERO);
        assertEquals(benchmarkStats.getKilledRequestsCount(), 0);
        assertEquals(benchmarkStats.getSucceedRequestsCount(), 0);
        assertEquals(benchmarkStats.getFailedRequestsCount(), 0);
        assertEquals(benchmarkStats.getTransmittedBytes(), 0);
        assertEquals(benchmarkStats.getTotalRequestsCount(), 0);
    }

    @Test
    public void filledStatsReturnEqualValuesInGetters() {
        BenchmarkStats benchmarkStats = new BenchmarkStats();

        benchmarkStats.addRequest(true, 500, Duration.ofMillis(400));
        benchmarkStats.addRequest(true, 500, Duration.ofMillis(300));
        benchmarkStats.addRequest(true, 1000, Duration.ofMillis(1000));

        assertTrue(benchmarkStats.getRequestsPerSecond() > 0);
        assertTrue(benchmarkStats.getAverageRequestDuration() != Duration.ZERO);
        assertTrue(benchmarkStats.getRequestsTotalDuration() != Duration.ZERO);
        assertEquals(benchmarkStats.getKilledRequestsCount(), 0);
        assertEquals(benchmarkStats.getSucceedRequestsCount(), 3);
        assertEquals(benchmarkStats.getFailedRequestsCount(), 0);
        assertEquals(benchmarkStats.getTransmittedBytes(), 2000);
        assertEquals(benchmarkStats.getTotalRequestsCount(), 3);
    }
}
