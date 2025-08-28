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

    public List<Flight> searchFlights(double maxBudget, String departureAirport, String arrivalAirport, String climate, 
            LocalDate departureDate, Integer flexiDays, String tag) {
        
        LocalDate earliestDate = null;
        LocalDate latestDate = null;

        
        if (departureDate != null && flexiDays != null) {
            earliestDate = departureDate.minusDays(flexiDays);
            latestDate = departureDate.plusDays(flexiDays);
        } else if (departureDate != null) {
            earliestDate = departureDate;
            latestDate = departureDate;
        }

        List<Flight> flights = flightRepo.searchFlights(maxBudget, departureAirport, arrivalAirport, climate, departureDate, earliestDate, latestDate, tag);
        
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
        if (departureDate != null) {
            System.out.println("Desired departure Date = " + departureDate + ".");
        }
        if (flexiDays != null) {
            System.out.println("Number of flexi-days = " + flexiDays + ".");
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
