package com.cbfacademy.frugalflyer.flights.flight;


import java.util.List;

import org.springframework.stereotype.Service;



/**
 * Service class to manage flight objects
 */
@Service
public class FlightService {
    
    private final FlightRepository flightRepo;

    public FlightService(FlightRepository flightRepo) {
        this.flightRepo = flightRepo;
    }

    public List<Flight> searchFlights() {
        
        return flightRepo.searchFlights();
    }
    

}
