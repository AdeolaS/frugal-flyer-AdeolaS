package com.cbfacademy.frugalflyer.flights.destination;



import org.springframework.data.repository.ListCrudRepository;

/**
 * The DestinationRepository interface defines the operations for managing Destinations in the system.
 * It provides methods for retrieving Destination records.
 */
public interface DestinationRepository extends ListCrudRepository<Destination,Long> {
    
    public boolean existsByClimate(String climate);

    public boolean existsByTags(String tag);
}
