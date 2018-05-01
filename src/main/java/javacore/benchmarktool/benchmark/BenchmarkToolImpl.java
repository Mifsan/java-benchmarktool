package javacore.benchmarktool.benchmark;

import javacore.benchmarktool.http.*;

import java.io.PrintStream;

public class BenchmarkToolImpl implements BenchmarkTool {

    private BenchmarkStats benchmarkStats;
    private BenchmarkSettings benchmarkSettings;

    public BenchmarkToolImpl(BenchmarkSettings benchmarkSettings) {
        this.benchmarkSettings = benchmarkSettings;
    }

    public void run() {
        final BenchmarkStatsCollector reqListener = new BenchmarkStatsCollectorImpl();
        final HttpConnection httpConnect = new HttpConnectionImpl(benchmarkSettings.getTimeout());
        final HttpRequestPool requestPool = new HttpRequestPool(httpConnect, reqListener, benchmarkSettings.getConcurrencyLevel());
        requestPool.runRequests(benchmarkSettings.getTargetUrl(), benchmarkSettings.getRequestCount());
        benchmarkStats = reqListener.getStats();
    }

    public void showReport(PrintStream stream) {
        final BenchmarkReport report = new BenchmarkReportImpl(benchmarkStats, benchmarkSettings);
        stream.print(report.getReport());
    }

}
