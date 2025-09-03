package com.cbfacademy.frugalflyer.flights.airport;



import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;



/**
 * The AirportRepository interface defines the operations for managing Airports in the system.
 * It provides methods for retrieving Airport records.
 */
public interface AirportRepository extends ListCrudRepository<Airport,String> {
    
    @Transactional(readOnly = true)
    Airport findByCodeIgnoreCase(String code);

    @Modifying
    @Transactional
    public void deleteByCodeIgnoreCase(String code);
}
