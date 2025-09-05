package com.cbfacademy.frugalflyer.flights.externalFlight;

public class Arrival {
    private String iata;
    private String airport;

    public Arrival(String iata) {
        this.iata = iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIata() {
        return this.iata;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getAirport() {
        return this.airport;
    }
}
