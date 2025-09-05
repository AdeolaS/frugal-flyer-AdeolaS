package com.cbfacademy.frugalflyer.flights.airport;

import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbfacademy.frugalflyer.flights.flight.FlightRepository;

/**
 * Service class to manage airport objects
 */
@Service
public class AirportService {

    private final AirportRepository airportRepo;
    private final FlightRepository flightRepo;

    public AirportService(AirportRepository airportRepo, FlightRepository flightRepo) {
        this.airportRepo = airportRepo;
        this.flightRepo = flightRepo;
    }

    @Transactional
    public void deleteAirportByCode (String code) throws AirportNotFoundException, AirportInUseException {

        // If the airport doesn't exist
        if (airportRepo.findByCodeIgnoreCase(code) == null) {
            throw new AirportNotFoundException("Invalid Airport code: " + code + ". The airport you are trying to delete doesn't exist in the database.");
        } else {
            // If the airport exists and flights are flying to and from it
            if (flightRepo.existsByAirportCode(code)) {
                throw new AirportInUseException("Airport with code " + code + " is in use. Please enter an airport with no flights arriving at or departing from it.");
            } else {
                airportRepo.deleteByCodeIgnoreCase(code);
            }            
        }
    }

    @Transactional
    public Airport createNewAirport(Airport airport) throws IllegalArgumentException, OptimisticLockingFailureException {

        if (airport == null || airport.getCode() == null) {
            throw new IllegalArgumentException("Airport and its code cannot be equal to null");
        }
        if (!airport.getCode().matches("[A-Z]{3}")) {
            throw new IllegalArgumentException("Airport code must be a 3- capital letter IATA code.");
        }
        if (airportRepo.existsByCodeIgnoreCase(airport.getCode())) {
            throw new IllegalArgumentException("Airport IATA code already exists in the database.");
        }
        return airportRepo.save(airport);
    }

    @Transactional
    public Airport updateAirport(String code, Airport updatedAirport) 
    throws AirportNotFoundException, OptimisticLockingFailureException, IllegalArgumentException {

        if (code == null || updatedAirport == null) {
            throw new IllegalArgumentException("Code and updated airport must not be null");
        }

        Airport airport = airportRepo.findByCodeIgnoreCase(code);

        if (airport == null) {
            throw new AirportNotFoundException("Invalid Airport code: " + code + ". The airport you are trying to update doesn't exist in the database.");
        } else {

            airport.setName(updatedAirport.getName());
            airport.setDestination(updatedAirport.getDestination());

            return airportRepo.save(airport);
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
