package com.aooled_laptop.httpupload.error;

public class UnknowHostError extends Exception {
    public UnknowHostError() {
    }

    public UnknowHostError(String message) {
        super(message);
    }

    public UnknowHostError(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknowHostError(Throwable cause) {
        super(cause);
    }
}
