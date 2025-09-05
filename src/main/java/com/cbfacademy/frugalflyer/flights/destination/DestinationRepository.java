package com.cbfacademy.frugalflyer.flights.destination;

import org.springframework.data.repository.ListCrudRepository;

/**
 * The DestinationRepository interface defines the operations for managing Destinations in the system.
 * It provides methods for retrieving Destination records.
 */
public interface DestinationRepository extends ListCrudRepository<Destination,Long> {
    /**
     * Check if destination with given climate exists.
     * @param climate Type of climate
     * @return True or false
     */
    public boolean existsByClimate(String climate);

    /**
     * Check if destination with given tag exists.
     * @param tag tag string
     * @return True or false
     */
    public boolean existsByTags(String tag);
}
