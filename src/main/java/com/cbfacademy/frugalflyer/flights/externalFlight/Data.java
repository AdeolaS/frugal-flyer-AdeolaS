package com.cbfacademy.frugalflyer.flights.externalFlight;

public class Data {
    
    private Arrival arrival;
    private Departure departure;

    public Data(Arrival arrival, Departure departure) {
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
}
