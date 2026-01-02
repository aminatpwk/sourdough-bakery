package org.example.sourdough.exception;

public class SourdoughException extends RuntimeException {
    public SourdoughException(String message) {
        super(message);
    }

    public SourdoughException(String message, Throwable cause) {
        super(message, cause);
    }
}
