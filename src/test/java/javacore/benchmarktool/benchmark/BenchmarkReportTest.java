package javacore.benchmarktool.benchmark;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

public class BenchmarkReportTest extends Assert {

    @Test
    public void emptyStatsMustReportZeroValues() {
        BenchmarkStatsImpl benchmarkStatsImpl = new BenchmarkStatsImpl();
        BenchmarkSettingsImpl benchmarkSettingsImpl = new BenchmarkSettingsImpl();
        BenchmarkReportImpl report = new BenchmarkReportImpl(benchmarkStatsImpl, benchmarkSettingsImpl);

        String expectedReportOutput = "Concurrency level:     5\n" +
                "Total time spent:      0 sec\n" +
                "Total request count:   0\n" +
                "Failed request count:  0\n" +
                "Bytes transmitted:     0\n" +
                "Requests per second:   0,000000\n" +
                "Average request time:  0 sec\n" +
                "Percentiles:\n" +
                "50%    0 sec\n" +
                "80%    0 sec\n" +
                "90%    0 sec\n" +
                "95%    0 sec\n" +
                "99%    0 sec\n" +
                "100%    0 sec\n";

        assertEquals(report.getReport(), expectedReportOutput);
    }

    @Test
    public void filledStatsMustContainEqualValuesInReport() {
        BenchmarkStatsImpl benchmarkStatsImpl = new BenchmarkStatsImpl();
        BenchmarkSettingsImpl benchmarkSettingsImpl = new BenchmarkSettingsImpl();

        benchmarkStatsImpl.addRequest(true, 100, Duration.ofMillis(1000));
        benchmarkStatsImpl.addRequest(true, 200, Duration.ofMillis(1000));
        benchmarkStatsImpl.addRequest(false, 0, Duration.ofMillis(1000));

        BenchmarkReportImpl report = new BenchmarkReportImpl(benchmarkStatsImpl, benchmarkSettingsImpl);

        String expectedReportOutput = "Concurrency level:     5\n" +
                "Total time spent:      3 sec\n" +
                "Total request count:   3\n" +
                "Failed request count:  1\n" +
                "Bytes transmitted:     300\n" +
                "Requests per second:   1,000000\n" +
                "Average request time:  1 sec\n" +
                "Percentiles:\n" +
                "50%    1 sec\n" +
                "80%    1 sec\n" +
                "90%    1 sec\n" +
                "95%    1 sec\n" +
                "99%    1 sec\n" +
                "100%    1 sec\n";

        assertEquals(report.getReport(), expectedReportOutput);
    }

    @Test
    public void filledSettingsMustContainEqualValuesInReport() {
        BenchmarkStatsImpl benchmarkStatsImpl = new BenchmarkStatsImpl();
        BenchmarkSettingsImpl benchmarkSettingsImpl = new BenchmarkSettingsImpl();

        benchmarkSettingsImpl.setConcurrencyLevel(100);

        BenchmarkReportImpl report = new BenchmarkReportImpl(benchmarkStatsImpl, benchmarkSettingsImpl);

        String expectedReportOutput = "Concurrency level:     100\n" +
                "Total time spent:      0 sec\n" +
                "Total request count:   0\n" +
                "Failed request count:  0\n" +
                "Bytes transmitted:     0\n" +
                "Requests per second:   0,000000\n" +
                "Average request time:  0 sec\n" +
                "Percentiles:\n" +
                "50%    0 sec\n" +
                "80%    0 sec\n" +
                "90%    0 sec\n" +
                "95%    0 sec\n" +
                "99%    0 sec\n" +
                "100%    0 sec\n";

        assertEquals(report.getReport(), expectedReportOutput);
    }
}
