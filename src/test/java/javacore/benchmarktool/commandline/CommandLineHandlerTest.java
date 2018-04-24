package javacore.benchmarktool.commandline;

import javacore.benchmarktool.benchmark.BenchmarkSettings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.cli.*;

import java.net.URL;
import java.time.Duration;

public class CommandLineHandlerTest extends Assert {

    @Test(expected = RuntimeException.class)
    public void testSomeInvalidArgumentThrowsException() {
        new CommandLineHandler(new String[]{"--some"});
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidUrlThrowsRuntimeException() {
        new CommandLineHandler(new String[]{"--u=test"});
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidRequestCountThrowsRuntimeException() {
        new CommandLineHandler(new String[]{"--n=test"});
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidConcurrencyLevelThrowsRuntimeException() {
        new CommandLineHandler(new String[]{"--c=test"});
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidTimeoutThrowsRuntimeException() {
        new CommandLineHandler(new String[]{"--t=test"});
    }

    @Test
    public void testValidArgumentNoThrowNoErr() {
        String[] arguments = {"-u=http://test.ru/", "-n=2", "-c=27", "-t=44"};
        CommandLineHandler commandLineHandler = new CommandLineHandler(arguments);
        BenchmarkSettings settings = commandLineHandler.getSettings();

        assertEquals(settings.getTargetUrl().toString(), "http://test.ru/");
        assertEquals(settings.getRequestCount(), 2);
        assertEquals(settings.getConcurrencyLevel(), 27);
        assertEquals(settings.getTimeout(), Duration.ofMillis(44));
    }
}
