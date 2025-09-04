package com.cbfacademy.frugalflyer.flights.exceptions;

/**
 * Exception to be thrown when a given date is invalid.
 */
public class InvalidDateException extends Exception {
    private final String message;

    public InvalidDateException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return String.join(" - ", "InvalidDateException", this.message);
    }
}
