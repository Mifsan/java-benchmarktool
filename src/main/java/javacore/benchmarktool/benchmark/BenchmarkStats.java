package javacore.benchmarktool.benchmark;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BenchmarkStats {

    private int succeedRequestsCount = 0;
    private int failedRequestsCount = 0;
    private int killedRequestsCount = 0;
    private Duration requestsTotalDuration;
    private long transmittedBytes = 0;
    private List<Duration> timeSpent = new ArrayList<>();

    public void addRequest(boolean succeed, long byteCount, Duration duration) {
        if (succeed) {
            this.succeedRequestsCount += 1;
        } else {
            this.failedRequestsCount += 1;
        }

        this.transmittedBytes += byteCount;
        this.timeSpent.add(duration);
    }

    public void addKilledRequest() {
        killedRequestsCount++;
    }

    public float getRequestsPerSecond() {
        if (getTotalRequestsCount() == 0) {
            return 0;
        }

        return 1000.f * (float)getTotalRequestsCount() / (float)getRequestsTotalDuration().toMillis();
    }

    public Duration getAverageRequestDuration() {
        if(timeSpent.isEmpty()) {
            return Duration.ZERO;
        }

        return getRequestsTotalDuration().dividedBy(timeSpent.size());
    }

    public Duration getDurationValueByPercentile(int percentile) {
        if (timeSpent.size() == 0) {
            return Duration.ZERO;
        }
        timeSpent.sort(null);
        percentile = percentile > 100 ? 100 : percentile < 0 ? 0 : percentile; // магия аутиста, если больше 100 - присвой 100, если меньше 0 присвой 0.
        int index = timeSpent.size() * percentile / 100 - 1;
        return timeSpent.get(index);
    }

    public Duration getRequestsTotalDuration() {
        return timeSpent.stream().reduce(Duration.ZERO, Duration::plus);
    }

    public int getKilledRequestsCount() {
        return killedRequestsCount;
    }

    public int getSucceedRequestsCount() {
        return succeedRequestsCount;
    }

    public int getFailedRequestsCount() {
        return failedRequestsCount;
    }

    public long getTransmittedBytes() {
        return transmittedBytes;
    }

    public int getTotalRequestsCount() {
        return succeedRequestsCount + killedRequestsCount + failedRequestsCount;
    }

}
