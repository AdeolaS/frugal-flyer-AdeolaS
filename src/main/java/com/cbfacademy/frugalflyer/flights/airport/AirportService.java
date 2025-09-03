package com.cbfacademy.frugalflyer.flights.airport;

import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.AirportInUseException;
import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.AirportNotFoundException;
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
