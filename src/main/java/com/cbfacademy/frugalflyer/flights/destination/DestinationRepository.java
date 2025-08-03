package com.cbfacademy.frugalflyer.flights.destination;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

public interface DestinationRepository extends ListCrudRepository<Destination, Long>{
    List<Destination> findByClimate(String Climate);
}
