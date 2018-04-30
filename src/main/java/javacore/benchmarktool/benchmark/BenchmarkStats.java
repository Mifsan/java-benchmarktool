package javacore.benchmarktool.benchmark;

import java.time.Duration;

public interface BenchmarkStats {
    float getRequestsPerSecond();
    Duration getAverageRequestDuration();
    Duration getDurationValueByPercentile(int percentile);
    Duration getRequestsTotalDuration();
    int getKilledRequestsCount();
    int getSucceedRequestsCount();
    int getFailedRequestsCount();
    long getTransmittedBytes();
    int getTotalRequestsCount();
}
