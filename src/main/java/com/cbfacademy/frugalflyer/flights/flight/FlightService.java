package com.cbfacademy.frugalflyer.flights.flight;


import java.time.LocalDate;
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

    public List<Flight> searchFlights(double maxBudget, String departureAirport, String arrivalAirport, String climate, LocalDate departureDate) {
        
        List<Flight> flights = flightRepo.searchFlights(maxBudget, departureAirport, arrivalAirport, climate, departureDate);
        
        if (flights.isEmpty()) {
            throw new RuntimeException("No flights available");
        }
        
        return flights;
    }
    
    public Flight findRandomFlight(String departureAirport) {

        Flight flight = flightRepo.findRandomFlight(departureAirport);

        if (flight == null) {
            throw new RuntimeException("No flights available from departure airport: " + departureAirport);
        }
        return flight;
    }

}
