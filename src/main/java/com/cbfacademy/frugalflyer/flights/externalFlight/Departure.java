package com.cbfacademy.frugalflyer.flights.externalFlight;

public class Departure {
    private String iata;

    public Departure(String iata) {
        this.iata = iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIata() {
        return this.iata;
    }
}
