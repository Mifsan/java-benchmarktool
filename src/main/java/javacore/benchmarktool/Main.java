package javacore.benchmarktool;

import java.net.MalformedURLException;
import java.time.Duration;
import java.net.URL;

import javacore.benchmarktool.benchmark.BenchmarkTool;
import javacore.benchmarktool.commandline.CommandLineHandler;

class Main {

    public static void main(String[] arguments) {

        try {
            CommandLineHandler commandLineHandler = new CommandLineHandler(arguments);
            BenchmarkTool benchmarkTool = new BenchmarkTool(commandLineHandler.getSettings());
            benchmarkTool.run();
            benchmarkTool.showReport(System.out);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
