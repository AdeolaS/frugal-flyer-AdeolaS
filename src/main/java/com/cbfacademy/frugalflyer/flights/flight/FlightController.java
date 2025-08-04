package com.cbfacademy.frugalflyer.flights.flight;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.frugalflyer.flights.airport.AirportService;
import com.cbfacademy.frugalflyer.flights.airport.Airport;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/flights")
public class FlightController {
    
    private final FlightService flightService;
    private final AirportService airportService;

    public FlightController(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    @GetMapping
    public List<Flight> getAllflights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/search")
    public List<Flight> getFlights(@RequestParam String originAirport, 
                    @RequestParam (required = false) String destinationAirport, 
                    @RequestParam (defaultValue = "1500") double maxBudget) {
        
        return flightService.getFlights(originAirport, destinationAirport, maxBudget);
    }
    
    @GetMapping("/destination")
    // Spring can't convert the Airport object type from the request parameter so must receive a string instead.
    public List<Flight> findByDestinationAirport(@RequestParam String destinationAirportCode) {

        // Finds Destination Airport using the airport code
        Airport destinationAirport = airportService.findAirportById(destinationAirportCode);
        return flightService.findByDestinationAirport(destinationAirport);
    }
    
    // @GetMapping("/climate")
    // public List<Flight> findByClimate(@RequestParam String climate) {

    // }

}
