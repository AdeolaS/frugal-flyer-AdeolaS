package com.cbfacademy.frugalflyer.flights.customExceptions;

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
