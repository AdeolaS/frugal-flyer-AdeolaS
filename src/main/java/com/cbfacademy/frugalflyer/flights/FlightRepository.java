package com.cbfacademy.frugalflyer.flights;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

public interface FlightRepository extends ListCrudRepository<Flight, Long> {
	
    List<Flight> findByDestinationAirport(Airport destinationAirport);
}
