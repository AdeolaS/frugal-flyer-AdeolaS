package com.cbfacademy.frugalflyer.flights;

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

    public List<Flight> getAllFlights() {
        return flightRepo.findAll();
    }

    // public Flight getRandomFlight() {

    // }

    public List<Flight> findByDestinationAirport(Airport destinationAirport) {
        return flightRepo.findByDestinationAirport(destinationAirport);
    }


}
