package com.github.steromano87.onigiri.enhancers.syncing;

public class SyncException extends RuntimeException {
    public SyncException() {
        super();
    }

    public SyncException(String message) {
        super(message);
    }

    public SyncException(String message, Throwable cause) {
        super(message, cause);
    }

    public SyncException(Throwable cause) {
        super(cause);
    }
}
