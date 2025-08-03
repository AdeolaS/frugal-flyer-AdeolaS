package com.cbfacademy.frugalflyer.flights.flight;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.cbfacademy.frugalflyer.flights.airport.Airport;

public interface FlightRepository extends ListCrudRepository<Flight, Long> {
	
    List<Flight> findByDestinationAirport(Airport destinationAirport);

    // List<Flight> findByClimate(String climate);
}
