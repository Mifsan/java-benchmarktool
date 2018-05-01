package javacore.benchmarktool.benchmark;

import java.io.PrintStream;

public interface BenchmarkTool {
    void run();
    void showReport(PrintStream stream);
}
