package javacore.benchmarktool;

import javacore.benchmarktool.benchmark.BenchmarkTool;
import javacore.benchmarktool.benchmark.BenchmarkToolImpl;
import javacore.benchmarktool.commandline.CommandLineHandler;

class Main {

    public static void main(String[] arguments) {

        try {
            CommandLineHandler commandLineHandler = new CommandLineHandler(arguments);
            BenchmarkTool benchmarkTool = new BenchmarkToolImpl(commandLineHandler.getSettings());
            benchmarkTool.run();
            benchmarkTool.showReport(System.out);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
