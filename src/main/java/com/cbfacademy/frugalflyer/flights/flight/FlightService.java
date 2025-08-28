package com.cbfacademy.frugalflyer.flights.flight;


import java.util.List;

import org.springframework.stereotype.Service;



/**
 * Service class to manage flight objects
 */
@Service
public class FlightService {
    
    private final FlightRepository flightRepo;

     /**
     * Constructor for FlightService.
     *
     * @param flightRepo the FlightRepository to be used for data persistence
     */
    public FlightService(FlightRepository flightRepo) {
        this.flightRepo = flightRepo;
    }

    public List<Flight> searchFlights(double maxBudget, String departureAirport, String arrivalAirport, String climate) {
        
        return flightRepo.searchFlights(maxBudget, departureAirport, arrivalAirport, climate);
    }
    
    public Flight findRandomFlight(String departureAirport) {

        Flight flight = flightRepo.findRandomFlight(departureAirport);

        if (flight == null) {
            throw new RuntimeException("No flights available");
    }
    return flight;
    }

}
