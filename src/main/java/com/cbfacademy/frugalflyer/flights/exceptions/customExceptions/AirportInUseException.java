package com.cbfacademy.frugalflyer.flights.exceptions.customExceptions;

/**
 * Exception to be thrown when a given airport cannot be found.
 */
public class AirportInUseException extends Exception {
    private final String message;

    public AirportInUseException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return String.join(" - ", "AirportInUseException", this.message);
    }
}
