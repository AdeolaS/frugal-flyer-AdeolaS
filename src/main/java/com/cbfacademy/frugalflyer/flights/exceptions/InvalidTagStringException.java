package com.cbfacademy.frugalflyer.flights.exceptions;

/**
 * Exception to be thrown when a given tag string cannot be found.
 */
public class InvalidTagStringException extends Exception {
    private final String message;

    public InvalidTagStringException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return String.join(" - ", "InvalidTagStringException", this.message);
    }
}
