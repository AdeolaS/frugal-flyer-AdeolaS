package com.cbfacademy.frugalflyer.flights.flight;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * The FlightRepository interface defines the operations for managing Flights in the system.
 * It provides methods for retrieving Flight records.
 */
public interface FlightRepository extends ListCrudRepository<Flight, Long> {
	
    /**
     * Searches for flights that match the search requirements of the imput
     * @param maxBudget the maximum price for a flight
     * @param departureAirport the aiport which the plane will leave from
     * @param arrivalAirport the airport at which the plane will land
     * @param climate the climate of the destination
     * @return a list of flights that match the search specifications
     */
    @Query("SELECT f FROM Flight f " + 
            "WHERE f.price <= :maxBudget " +
            "AND LOWER(f.departureAirport.code) = LOWER(:departureAirport) " +
            "AND (:arrivalAirport IS NULL OR LOWER(f.arrivalAirport.code) = LOWER(:arrivalAirport)) " +
            "AND (:climate IS NULL OR LOWER(f.arrivalAirport.destination.climate) = LOWER(:climate))"
           )
    public List<Flight> searchFlights(
        @Param ("maxBudget") double maxBudget,
        @Param ("departureAirport") String departureAirport,
        @Param ("arrivalAirport") String arrivalAirport,
        @Param ("climate") String climate
    );


    /**
     * Returns a random flight from the specified departure airport
     * @param departureAirport the aiport which the plane will leave from
     * @return a random flight from the specified departure airport
     */
    @Query("SELECT f FROM Flight f " +
            "WHERE LOWER(f.departureAirport.code) = LOWER(:departureAirport) " + 
            "ORDER BY RAND() LIMIT 1"
            )
    public Flight findRandomFlight(
        @Param ("departureAirport") String departureAirport
    );

}
