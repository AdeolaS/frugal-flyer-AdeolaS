package com.cbfacademy.frugalflyer.flights.flight;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
     * @param departureDate departure date of flight
     * @return a list of flights that match the search specifications
     */
    @Transactional(readOnly = true)
    @Query("SELECT f FROM Flight f " + 
            "WHERE f.price <= :maxBudget " +
            "AND LOWER(f.departureAirport.code) = LOWER(:departureAirport) " +
            "AND (:arrivalAirport IS NULL OR LOWER(f.arrivalAirport.code) = LOWER(:arrivalAirport)) " +
            "AND (:departureDate IS NULL OR f.departureDate BETWEEN :earliestDate AND :latestDate)" +
            "ORDER BY f.price ASC"
           )
    public List<Flight> searchFlightsUsingArrivalAirport(
        @Param ("maxBudget") double maxBudget,
        @Param ("departureAirport") String departureAirport,
        @Param ("arrivalAirport") String arrivalAirport,
        @Param ("departureDate") LocalDate departureDate,
        @Param ("earliestDate") LocalDate earliestDate,
        @Param ("latestDate") LocalDate latestDate
    );


    /**
     * Searches for flights that match the search requirements of the imput
     * @param maxBudget the maximum price for a flight
     * @param departureAirport the aiport which the plane will leave from
     * @param climate the climate of the destination
     * @param departureDate departure date of flight
     * @param tag descriptive tag of type of holiday
     * @return a list of flights that match the search specifications
     */
    @Transactional(readOnly = true)
    @Query("SELECT f FROM Flight f " + 
            "WHERE f.price <= :maxBudget " +
            "AND LOWER(f.departureAirport.code) = LOWER(:departureAirport) " +
            "AND (:climate IS NULL OR LOWER(f.arrivalAirport.destination.climate) LIKE LOWER(CONCAT('%', :climate, '%')))" +
            "AND (:departureDate IS NULL OR f.departureDate BETWEEN :earliestDate AND :latestDate)" +
            "AND (:tag IS NULL OR :tag MEMBER OF f.arrivalAirport.destination.tags)" +
            "ORDER BY f.price ASC"
           )
    public List<Flight> searchFlightsUsingClimateAndTags(
        @Param ("maxBudget") double maxBudget,
        @Param ("departureAirport") String departureAirport,
        @Param ("climate") String climate,
        @Param ("departureDate") LocalDate departureDate,
        @Param ("earliestDate") LocalDate earliestDate,
        @Param ("latestDate") LocalDate latestDate,
        @Param ("tag") String tag
    );


    /**
     * Returns a random flight from the specified departure airport
     * @param departureAirport the aiport which the plane will leave from
     * @return a random flight from the specified departure airport
     */
    @Transactional(readOnly = true)
    @Query("SELECT f FROM Flight f " +
            "WHERE LOWER(f.departureAirport.code) = LOWER(:departureAirport) " + 
            "ORDER BY RAND() LIMIT 1"
            )
    public Flight findRandomFlight(
        @Param ("departureAirport") String departureAirport
    );


    /**
     * Returns flights that leave and arrive at the specified airports
     * @param departureAirport the aiport which the plane will leave from
     * @param arrivalAirport the airport at which the plane will land
     * @return
     */
    @Transactional(readOnly = true)
    @Query("SELECT f FROM Flight f " + 
            "WHERE LOWER(f.departureAirport.code) = LOWER(:departureAirport) " +
            "AND LOWER(f.arrivalAirport.code) = LOWER(:arrivalAirport) "
           )
    public List<Flight> searchFlightsUsingAirportsOnly(
        @Param ("departureAirport") String departureAirport,
        @Param ("arrivalAirport") String arrivalAirport
    );

    @Transactional(readOnly = true)
    // if there's more than 0 matching flights, returns true. Else, false
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
        "FROM Flight f " +
        "WHERE LOWER(f.departureAirport.code) = LOWER(:code) " +
        "   OR LOWER(f.arrivalAirport.code) = LOWER(:code)")
    public boolean existsByAirportCode(@Param("code") String code);
}
