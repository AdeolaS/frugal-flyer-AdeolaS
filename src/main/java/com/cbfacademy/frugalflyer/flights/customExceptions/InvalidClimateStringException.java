package com.cbfacademy.frugalflyer.flights.customExceptions;

public class InvalidClimateStringException extends Exception {
    private final String message;

    public InvalidClimateStringException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return String.join(" - ", super.getMessage(), this.message);
    }
}
