package com.cbfacademy.frugalflyer.flights;

import org.springframework.data.repository.ListCrudRepository;

public interface FlightRepository extends ListCrudRepository<Flight, Long> {
	
}
