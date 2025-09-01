package com.cbfacademy.frugalflyer.flights.exceptions.customExceptions;

/**
 * Exception to be thrown when a given nnumber is out of bounds.
 */
public class InvalidNumberException extends Exception {
    private final String message;

    public InvalidNumberException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return String.join(" - ", super.getMessage(), this.message);
    }
}
