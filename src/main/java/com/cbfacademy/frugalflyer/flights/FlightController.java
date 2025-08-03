package com.cbfacademy.frugalflyer.flights;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/flights")
public class FlightController {
    
    private final FlightService flightService;
    private final AirportRepository airportRepo;

    public FlightController(FlightService flightService, AirportRepository airportRepo) {
        this.flightService = flightService;
        this.airportRepo = airportRepo;
    }

    @GetMapping
    public List<Flight> getAllflights() {
        return flightService.getAllFlights();
    }
    
    @GetMapping("/destination")
    // Spring can't convert the Airport object type from the request parameter so must receive a string instead.
    public List<Flight> findByDestinationAirport(@RequestParam String destinationAirportCode) {

        Airport destinationAirport = airportRepo.findById(destinationAirportCode.toUpperCase()).orElseThrow();
        return flightService.findByDestinationAirport(destinationAirport);
        
    }
    

}
