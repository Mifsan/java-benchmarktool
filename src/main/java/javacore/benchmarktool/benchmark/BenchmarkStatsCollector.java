package javacore.benchmarktool.benchmark;

import javacore.benchmarktool.http.HttpRequestListener;

public interface BenchmarkStatsCollector extends HttpRequestListener{
    BenchmarkStats getStats();
}
