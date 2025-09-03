package com.cbfacademy.frugalflyer.flights.airport;

import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.AirportNotFoundException;

/**
 * Service class to manage airport objects
 */
@Service
public class AirportService {

    private final AirportRepository airportRepo;

    public AirportService(AirportRepository airportRepo) {
        this.airportRepo = airportRepo;
    }

    @Transactional
    public void deleteAirportByCode (String code) throws AirportNotFoundException {

        if (airportRepo.findByCodeIgnoreCase(code) == null) {
            throw new AirportNotFoundException("Invalid Airport code: " + code + ". The airport you are trying to delete doesn't exist in the database.");
        } else {
            airportRepo.deleteByCodeIgnoreCase(code);
        }
    }

    @Transactional
    public Airport createNewAirport(Airport airport) throws IllegalArgumentException, OptimisticLockingFailureException {

        return airportRepo.save(airport);
    }

    @Transactional
    public void updateAirport(String code, Airport updatedAirport) throws AirportNotFoundException {

        if (airportRepo.findByCodeIgnoreCase(code) == null) {
            throw new AirportNotFoundException("Invalid Airport code: " + code + ". The airport you are trying to update doesn't exist in the database.");
        } else {
            Airport airport = airportRepo.findByCodeIgnoreCase(code);

            airport.setCode(updatedAirport.getCode());
            airport.setName(updatedAirport.getName());
            airport.setDestination(updatedAirport.getDestination());
        }
    }

    public List<Airport> getAllAirports() {

        return airportRepo.findAll();
    }

    public Airport getAirportByCode(String code) throws AirportNotFoundException {
        
        if (airportRepo.findByCodeIgnoreCase(code) == null) {
            throw new AirportNotFoundException("Invalid Airport code: " + code + ". Please insert an airport that is recognised by this application.");
        } else {
            return airportRepo.findByCodeIgnoreCase(code);
        }
    }
}
