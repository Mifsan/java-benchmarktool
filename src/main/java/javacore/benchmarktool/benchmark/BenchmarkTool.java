package javacore.benchmarktool.benchmark;

import javacore.benchmarktool.http.*;

import java.io.PrintStream;

public class BenchmarkTool {

    private BenchmarkStats benchmarkStats = new BenchmarkStats();
    private BenchmarkSettings benchmarkSettings;

    public BenchmarkTool(BenchmarkSettings benchmarkSettings) {
        this.benchmarkSettings = benchmarkSettings;
    }

    public void run() {
        final HttpRequestListener reqListener = new HttpRequestListenerImpl(benchmarkStats);
        final HttpConnection httpConnect = new HttpConnectionImpl(benchmarkSettings.getTimeout());
        final HttpRequestPool requestPool = new HttpRequestPool(httpConnect, reqListener, benchmarkSettings.getConcurrencyLevel());
        requestPool.runRequests(benchmarkSettings.getTargetUrl(), benchmarkSettings.getRequestCount());
    }

    public void showReport(PrintStream stream) {
        final BenchmarkReport report = new BenchmarkReport(benchmarkStats, benchmarkSettings);
        stream.print(report.getReport());
    }

}
