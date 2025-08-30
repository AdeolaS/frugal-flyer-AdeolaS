package com.cbfacademy.frugalflyer.flights.flight;


import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cbfacademy.frugalflyer.flights.customExceptions.InvalidDateException;



/**
 * Service class to manage flight objects
 */
@Service
public class FlightService {
    
    private final FlightRepository flightRepo;

    private LocalDate earliestDate = null;
    private LocalDate latestDate = null;
    private LocalDate today = LocalDate.now();

    /**
    * Constructor for FlightService.
    *
    * @param flightRepo the FlightRepository to be used for data persistence
    */
    public FlightService(FlightRepository flightRepo) {
        this.flightRepo = flightRepo;
    }

    /**
     * Helper method to calculate the desired departure date range
     * If departure date and flexiday value have been supplied, subtracts and adds the flexidays from and to the departure date to find range of dates.
     * If earliest date is before today's date, the value is set to today's date.
     * If only departure date is supplied, set the range of dates to that departureDate value.
     * @param departureDate Date that the flight will depart from the departure airport
     * @param flexiDays Number of flexible days to be subtracted and added from the departure date to allow a range of departure dates
     * @return Range of acceptable departure dates.
     */
    private LocalDate[] setDateRange(LocalDate departureDate, Integer flexiDays) {

        if (departureDate != null && flexiDays != null) {

            latestDate = departureDate.plusDays(flexiDays);
            earliestDate = departureDate.minusDays(flexiDays);
            if (earliestDate.isBefore(today)) {
                earliestDate = today;
            }
            
        } else if (departureDate != null) {
            earliestDate = departureDate;
            latestDate = departureDate;
        }

        return new LocalDate[] {earliestDate,latestDate};
    }

    /**
    * Retrieves flights, applying filters using given paramenters
    * @param maxBudget Maximum cost of flight
    * @param departureAirport Airport that the flight will depart from
    * @param arrivalAirport Airport that the flight will arrive at
    * @param departureDate Date that the flight will depart from the departure airport
    * @param flexiDays Number of flexible days to be subtracted and added from the departure date to allow a range of departure dates
    * @return List of flights that match the given search criteria
    */
    public List<Flight> searchFlightsUsingArrivalAirport(double maxBudget, String departureAirport, String arrivalAirport, 
            LocalDate departureDate, Integer flexiDays) throws InvalidDateException {
        
        List<Flight> flights;
        LocalDate[] dateRange;

        if (today.isAfter(departureDate)) {
            throw new InvalidDateException("Departure date must be today's date or later.");
        }

        dateRange = setDateRange(departureDate, flexiDays);

        flights = flightRepo.searchFlightsUsingArrivalAirport(maxBudget, departureAirport, arrivalAirport, departureDate, dateRange[0], dateRange[1]);
        
        //A series of print messages that show to specified criteria
        System.out.println("\n------------------------------------");
        System.out.println("Criteria: ");
        System.out.println("Maximum Budget = " + maxBudget + ".");
        System.out.println("Departure Airport = " + departureAirport + ".");

        if (arrivalAirport != null) {
            System.out.println("Arrival Airport = " + arrivalAirport + ".");
        }
        if (departureDate != null && flexiDays== null) {
            System.out.println("Desired departure Date = " + departureDate + ".");
        }
        if (departureDate != null && flexiDays!= null) {
            System.out.println("Desired departure date range = " + earliestDate + " to " + latestDate + ".");
        }
        System.out.println("\n" + flights.size() + " flight(s) found.");
        System.out.println("------------------------------------");

        return flights;
    }

    /**
    * Retrieves flights, applying filters using given paramenters
    * @param maxBudget Maximum cost of flight
    * @param departureAirport Airport that the flight will depart from
    * @param climate Climate of the destination that the flight will arrive at
    * @param departureDate Date that the flight will depart from the departure airport
    * @param flexiDays Number of flexible days to be subtracted and added from the departure date to allow a range of departure dates
    * @param tag Descriptive tag that describes the type of holiday desired
    * @return List of flights that match the given search criteria
    */
    public List<Flight> searchFlightsUsingClimateAndTags(double maxBudget, String departureAirport, String climate, 
            LocalDate departureDate, Integer flexiDays, String tag) {

        List<Flight> flights;
        
        // If departure date and flexiday value have been supplied, subtract and add the flexidays from the departure date to find range of dates.
        // If only departure date is supplied, set the rnage of dates to that departureDate value.
        if (departureDate != null && flexiDays != null) {

            earliestDate = departureDate.minusDays(flexiDays);
            latestDate = departureDate.plusDays(flexiDays);

            if (earliestDate.isBefore(today)) {
                earliestDate = today;
            }
        } else if (departureDate != null) {
            earliestDate = departureDate;
            latestDate = departureDate;
        }

        flights = flightRepo.searchFlightsUsingClimateAndTags(maxBudget, departureAirport, climate, departureDate, earliestDate, latestDate, tag);
        
        //A series of print messages that show to specified criteria
        System.out.println("\n------------------------------------");
        System.out.println("Criteria: ");
        System.out.println("Maximum Budget = " + maxBudget + ".");
        System.out.println("Departure Airport = " + departureAirport + ".");
        
        if (climate != null) {
            System.out.println("Desired Climate = " + climate + ".");
        }
        if (departureDate != null && flexiDays== null) {
            System.out.println("Desired departure Date = " + departureDate + ".");
        }
        if (departureDate != null && flexiDays!= null) {
            System.out.println("Desired departure date range = " + earliestDate + " to " + latestDate + ".");
        }
        if (tag != null) {
            System.out.println("Desired Destination Tag = " + tag + ".");
        }
        System.out.println("\n" + flights.size() + " flight(s) found.");
        System.out.println("------------------------------------");

        return flights;
    }
    
    /**
     * Finds a single random flight that leaves from the specified airport
     * @param departureAirport Airport that the flight will depart from
     * @return random flight
     */
    public Flight findRandomFlight(String departureAirport) {

        Flight flight = flightRepo.findRandomFlight(departureAirport);

        return flight;
    }

}
