package com.cbfacademy.frugalflyer.flights.customExceptions;

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
