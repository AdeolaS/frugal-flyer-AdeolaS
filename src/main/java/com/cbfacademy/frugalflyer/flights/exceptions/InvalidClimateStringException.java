package com.cbfacademy.frugalflyer.flights.exceptions;

/**
 * Exception to be thrown when a given climate cannot be found.
 */
public class InvalidClimateStringException extends Exception {
    private final String message;

    public InvalidClimateStringException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return String.join(" - ", "InvalidClimateStringException", this.message);
    }
}
