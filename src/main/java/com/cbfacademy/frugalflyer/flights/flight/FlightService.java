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

    public List<Flight> searchFlights(double maxBudget, String departureAirport, String arrivalAirport, String climate, LocalDate departureDate, String tag) {
        
        List<Flight> flights = flightRepo.searchFlights(maxBudget, departureAirport, arrivalAirport, climate, departureDate, tag);
        
        if (flights.isEmpty()) {
            throw new RuntimeException("No flights available");
        }
        
        System.out.println("\n------------------------------------");
        System.out.println("Criteria: ");
        System.out.println("Maximum Budget = " + maxBudget + ".");
        System.out.println("Departure Airport = " + departureAirport + ".");
        
        if (arrivalAirport != null) {
            System.out.println("Arrival Airport = " + arrivalAirport + ".");
        }
        if (climate != null) {
            System.out.println("Desired Climate = " + climate + ".");
        }
        if (tag != null) {
            System.out.println("Desired Destination Tag = " + tag + ".");
        }
        System.out.println("\n" + flights.size() + " flight(s) found.");
        System.out.println("------------------------------------");

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
