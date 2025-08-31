package com.cbfacademy.frugalflyer.flights.airport;


import org.springframework.data.repository.ListCrudRepository;

/**
 * The AirportRepository interface defines the operations for managing Airports in the system.
 * It provides methods for retrieving Airport records.
 */
public interface AirportRepository extends ListCrudRepository<Airport,String> {
    
}
