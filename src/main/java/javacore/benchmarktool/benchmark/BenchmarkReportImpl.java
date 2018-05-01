package javacore.benchmarktool.benchmark;

import java.time.Duration;
import java.util.Arrays;

public class BenchmarkReportImpl implements BenchmarkReport {


    private BenchmarkStats benchmarkStats;
    private BenchmarkSettings benchmarkSettings;
    private final int[] PERCENTILES = {50, 80, 90, 95, 99, 100};

    BenchmarkReportImpl(BenchmarkStats benchmarkStats, BenchmarkSettings benchmarkSettings) {
        this.benchmarkStats = benchmarkStats;
        this.benchmarkSettings = benchmarkSettings;
    }

    public String getReport() {
        StringBuilder report = new StringBuilder();
        report.append(String.format("Concurrency level:     %s\n", benchmarkSettings.getConcurrencyLevel()));
        report.append(String.format("Total time spent:      %s\n", formatDuration(benchmarkStats.getRequestsTotalDuration())));
        report.append(String.format("Total request count:   %d\n", benchmarkStats.getTotalRequestsCount()));
        report.append(String.format("Failed request count:  %d\n", benchmarkStats.getFailedRequestsCount()));
        report.append(String.format("Bytes transmitted:     %d\n", benchmarkStats.getTransmittedBytes()));
        report.append(String.format("Requests per second:   %f\n", benchmarkStats.getRequestsPerSecond()));
        report.append(String.format("Average request time:  %s\n", formatDuration(benchmarkStats.getAverageRequestDuration())));
        report.append("Percentiles:\n");
        Arrays.stream(PERCENTILES).forEach(percentile -> report.append(String.format("%d%%    %s\n", percentile, formatDuration(benchmarkStats.getDurationValueByPercentile(percentile)))));
        return report.toString();
    }

    private String formatDuration(Duration duration) {
        final int millis = (int) (duration.toMillis() % 1000);
        final StringBuilder builder = new StringBuilder();
        builder.append(Long.toString(duration.getSeconds()));
        if (millis != 0) {
            builder.append(".").append(String.format("%03d", millis));
        }
        builder.append(" sec");

        return builder.toString();
    }
}
