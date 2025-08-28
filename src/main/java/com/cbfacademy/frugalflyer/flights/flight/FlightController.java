package com.cbfacademy.frugalflyer.flights.flight;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/flights")
public class FlightController {
    
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/search")
    public List<Flight> searchFlights(
            @RequestParam (defaultValue = "1500") double maxBudget,
            @RequestParam String departureAirport,
            @RequestParam (required = false) String arrivalAirport,
            @RequestParam (required = false) String climate
            ) {
        return flightService.searchFlights(maxBudget, departureAirport, arrivalAirport, climate);
    }

    @GetMapping("/surpriseMe")
    public Flight findRandomFlight(@RequestParam String departureAirport) {
        return flightService.findRandomFlight(departureAirport);
    }
    

}
