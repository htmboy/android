package com.aooled_laptop.aooled.error;

public class TimeoutError extends Exception{
    public TimeoutError() {
    }

    public TimeoutError(String message) {
        super(message);
    }

    public TimeoutError(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeoutError(Throwable cause) {
        super(cause);
    }
}
