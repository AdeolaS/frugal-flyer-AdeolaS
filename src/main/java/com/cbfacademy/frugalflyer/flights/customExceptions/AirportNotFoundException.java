package com.cbfacademy.frugalflyer.flights.customExceptions;

/**
 * Exception to be thrown when a given airport cannot be found.
 */
public class AirportNotFoundException extends Exception {
    private final String message;

    public AirportNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return String.join(" - ", super.getMessage(), this.message);
    }
}
