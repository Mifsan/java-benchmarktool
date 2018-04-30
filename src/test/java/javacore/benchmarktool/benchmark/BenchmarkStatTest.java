package javacore.benchmarktool.benchmark;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

public class BenchmarkStatTest extends Assert {

    @Test
    public void emptyStatsReturnDefaultZeroValues() {
        BenchmarkStatsImpl benchmarkStatsImpl = new BenchmarkStatsImpl();

        assertEquals((long) benchmarkStatsImpl.getRequestsPerSecond(), 0);
        assertEquals(benchmarkStatsImpl.getAverageRequestDuration(), Duration.ZERO);
        assertEquals(benchmarkStatsImpl.getRequestsTotalDuration(), Duration.ZERO);
        assertEquals(benchmarkStatsImpl.getKilledRequestsCount(), 0);
        assertEquals(benchmarkStatsImpl.getSucceedRequestsCount(), 0);
        assertEquals(benchmarkStatsImpl.getFailedRequestsCount(), 0);
        assertEquals(benchmarkStatsImpl.getTransmittedBytes(), 0);
        assertEquals(benchmarkStatsImpl.getTotalRequestsCount(), 0);
    }

    @Test
    public void filledStatsReturnEqualValuesInGetters() {
        BenchmarkStatsImpl benchmarkStatsImpl = new BenchmarkStatsImpl();

        benchmarkStatsImpl.addRequest(true, 500, Duration.ofMillis(400));
        benchmarkStatsImpl.addRequest(true, 500, Duration.ofMillis(300));
        benchmarkStatsImpl.addRequest(true, 1000, Duration.ofMillis(1000));

        assertTrue(benchmarkStatsImpl.getRequestsPerSecond() > 0);
        assertTrue(benchmarkStatsImpl.getAverageRequestDuration() != Duration.ZERO);
        assertTrue(benchmarkStatsImpl.getRequestsTotalDuration() != Duration.ZERO);
        assertEquals(benchmarkStatsImpl.getKilledRequestsCount(), 0);
        assertEquals(benchmarkStatsImpl.getSucceedRequestsCount(), 3);
        assertEquals(benchmarkStatsImpl.getFailedRequestsCount(), 0);
        assertEquals(benchmarkStatsImpl.getTransmittedBytes(), 2000);
        assertEquals(benchmarkStatsImpl.getTotalRequestsCount(), 3);
    }
}
