package com.cbfacademy.frugalflyer.flights.externalFlight;

import java.time.LocalDate;

public class Data {
    
    private Arrival arrival;
    private Departure departure;
    private String flight_date;

    public Data(Arrival arrival, Departure departure, LocalDate flight_date) {
        this.arrival = arrival;
        this.departure = departure;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }

    public Arrival getArrival() {
        return this.arrival;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Departure getDeparture() {
        return this.departure;
    }

    public void setFlightDate(String flight_date) {
        this.flight_date = flight_date;
    }

    public String getFlightDate() {
        return this.flight_date;
    }

}
