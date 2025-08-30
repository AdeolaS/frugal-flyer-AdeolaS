package com.cbfacademy.frugalflyer.flights.customExceptions;

public class InvalidTagStringException extends Exception {
    private final String message;

    public InvalidTagStringException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return String.join(" - ", super.getMessage(), this.message);
    }
}
