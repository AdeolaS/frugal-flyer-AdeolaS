package com.cbfacademy.frugalflyer.flights.flight;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;


public interface FlightRepository extends ListCrudRepository<Flight, Long> {
	
    @Query("SELECT f FROM Flight f " + 
            "WHERE f.price <= :maxBudget " +
            "AND LOWER(f.departureAirport.code) = LOWER(:departureAirport) " +
            "AND (:climate IS NULL OR LOWER(f.arrivalAirport.destination.climate) = LOWER(:climate))"
           )
    List<Flight> searchFlights(
        @Param ("maxBudget") double maxBudget,
        @Param ("departureAirport") String departureAirport,
        @Param ("climate") String climate
    );

}
