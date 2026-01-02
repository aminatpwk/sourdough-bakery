package org.example.sourdough.exception;

public class InvalidTokenException extends SourdoughException{
    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
