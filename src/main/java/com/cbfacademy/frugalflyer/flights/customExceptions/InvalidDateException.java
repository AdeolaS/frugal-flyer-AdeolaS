package com.cbfacademy.frugalflyer.flights.customExceptions;

public class InvalidDateException extends Exception {
    private final String message;

    public InvalidDateException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return String.join(" - ", super.getMessage(), this.message);
    }
}
