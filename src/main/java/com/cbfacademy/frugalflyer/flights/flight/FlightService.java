package com.cbfacademy.frugalflyer.flights.flight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
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

    // @Query(value = "SELECT * FROM flights ORDER BY price",
    //         nativeQuery = true)
    // public List<Flight> findAllFlights(double price);

    public List<Flight> getFlights(String originAirport, String destinationAirport, double maxBudget, String climate) {
        //flightRepo.findAll().forEach(f -> System.out.println("Origin: " + f.getOriginAirport().getCode() + originAirport));
        //flightRepo.findAll().forEach(f -> System.out.println(f.getDestinationAirport().getDestination().getClimate()));

        List<Flight> flightsList = flightRepo.findAll().stream()
        .filter(f -> f.getOriginAirport().getCode().equalsIgnoreCase(originAirport))
        .filter(f -> f.getPrice() <= maxBudget)
        .collect(Collectors.toList());

        if (destinationAirport != null) {
            flightsList.stream()
            .filter(f -> f.getDestinationAirport().getCode().equalsIgnoreCase(destinationAirport))
            .collect(Collectors.toList());
        }

        //flightsList.forEach(f -> System.out.println(f.getDestinationAirport().getDestination().getClimate()));

        if (climate != null) {
            flightsList.stream()
            .filter(f -> f.getDestinationAirport().getDestination().getClimate().equalsIgnoreCase(climate))
            .collect(Collectors.toList());
        }

        

        // Bad request if they ask for unexpected search 
        // error not found for country that doesn't exist
        // when returning list of flights, check if it's empty and say no flights were found for the criteria
        return flightsList;
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
