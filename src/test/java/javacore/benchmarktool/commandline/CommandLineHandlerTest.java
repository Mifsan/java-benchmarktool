package javacore.benchmarktool.commandline;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.cli.*;

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
}
