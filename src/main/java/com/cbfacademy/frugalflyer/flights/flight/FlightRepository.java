package com.cbfacademy.frugalflyer.flights.flight;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;


public interface FlightRepository extends ListCrudRepository<Flight, Long> {
	
    @Query("SELECT f FROM Flight f "
           )
    List<Flight> searchFlights(
    );

}
