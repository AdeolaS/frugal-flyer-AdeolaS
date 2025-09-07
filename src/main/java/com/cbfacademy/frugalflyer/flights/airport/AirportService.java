package com.cbfacademy.frugalflyer.flights.airport;

import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbfacademy.frugalflyer.flights.destination.DestinationRepository;
import com.cbfacademy.frugalflyer.flights.flight.FlightRepository;

/**
 * Service class to manage airport objects
 */
@Service
public class AirportService {

    private final AirportRepository airportRepo;
    private final FlightRepository flightRepo;
    private final DestinationRepository destinationRepo;

    public AirportService(AirportRepository airportRepo, FlightRepository flightRepo, DestinationRepository destinationRepo) {
        this.airportRepo = airportRepo;
        this.flightRepo = flightRepo;
        this.destinationRepo = destinationRepo;
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
    public Airport createNewAirport(String airportCode, String airportName, Long destinationId) throws IllegalArgumentException, OptimisticLockingFailureException {

        if (airportCode == null || airportCode.isBlank() || airportName == null || airportName.isBlank()) {
            throw new IllegalArgumentException("Airport name and code cannot be empty.");
        }
        if (!airportCode.matches("[A-Z]{3}")) {
            throw new IllegalArgumentException("Airport code must be a 3- capital letter IATA code.");
        }
        if (airportRepo.existsByCodeIgnoreCase(airportCode)) {
            throw new IllegalArgumentException("Airport IATA code already exists in the database.");
        }
        if (!destinationRepo.existsById(destinationId)) {
            throw new IllegalArgumentException("There is no destination with this Id in the databae.");
        }
        return airportRepo.save(new Airport(airportCode, airportName, destinationRepo.findDestinationById(destinationId)));
    }

    @Transactional
    public Airport updateAirport(String oldAirportCode, String airportName, Long destinationId) 
    throws AirportNotFoundException, OptimisticLockingFailureException, IllegalArgumentException {

        if (oldAirportCode == null || oldAirportCode.isBlank() || airportName == null || airportName.isBlank()) {
            throw new IllegalArgumentException("Code and updated airport name must not be null");
        }
        if (!destinationRepo.existsById(destinationId)) {
            throw new IllegalArgumentException("There is no destination with this Id in the databae.");
        }
        Airport airport = airportRepo.findByCodeIgnoreCase(oldAirportCode);

        if (airport == null) {
            throw new AirportNotFoundException("Invalid Airport code: " + oldAirportCode + ". The airport you are trying to update doesn't exist in the database.");
        } else {

            airport.setName(airportName);
            airport.setDestination(destinationRepo.findDestinationById(destinationId));

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
