package com.cbfacademy.frugalflyer.flights.flight;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cbfacademy.frugalflyer.flights.airport.Airport;

/**
 * Service class to manage flight objects
 */
@Service
public class FlightService {
    
    private final FlightRepository flightRepo;

    public FlightService(FlightRepository flightRepo) {
        this.flightRepo = flightRepo;
    }

    public List<Flight> getAllFlights() {
        return flightRepo.findAll();
    }

    public List<Flight> getFlights(String originAirport, String destinationAirport, double maxBudget) {
        //flightRepo.findAll().forEach(f -> System.out.println("Origin: " + f.getOriginAirport().getCode() + originAirport));
        return flightRepo.findAll().stream()
        .filter(f -> f.getOriginAirport().getCode().equalsIgnoreCase(originAirport))
        .filter(f -> f.getDestinationAirport().getCode().equalsIgnoreCase(destinationAirport))
        .filter(f -> f.getPrice() <= maxBudget)
        .collect(Collectors.toList());
    }

    // public Flight getRandomFlight() {

    // }

    public List<Flight> findByDestinationAirport(Airport destinationAirport) {
        return flightRepo.findByDestinationAirport(destinationAirport);
    }

    // public List<Flight> findByClimate(String climate) {
    //     return flightRepo.findByClimate(climate);
    // }

}
