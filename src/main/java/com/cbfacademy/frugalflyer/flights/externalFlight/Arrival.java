package com.cbfacademy.frugalflyer.flights.externalFlight;

public class Arrival {
    private String iata;

    public Arrival(String iata) {
        this.iata = iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIata() {
        return this.iata;
    }
}
