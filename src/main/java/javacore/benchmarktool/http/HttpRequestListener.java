package javacore.benchmarktool.http;

import java.time.Duration;

public interface HttpRequestListener {
    void onRequestComplete(Duration timeSpent, long transmittedByteCount, int httpStatusCode);
    void onRequestTimeout();
    void onRequestError(RuntimeException ex);
}
