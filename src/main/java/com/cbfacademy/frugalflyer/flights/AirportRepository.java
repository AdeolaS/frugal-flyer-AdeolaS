package com.cbfacademy.frugalflyer.flights;

import org.springframework.data.repository.ListCrudRepository;

public interface AirportRepository extends ListCrudRepository<Airport, String> {
    
}
