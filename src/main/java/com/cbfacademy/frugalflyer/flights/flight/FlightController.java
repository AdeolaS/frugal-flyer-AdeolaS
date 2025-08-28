package com.cbfacademy.frugalflyer.flights.flight;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class to implement flights API endpoints.
 */
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
            @RequestParam (required = false) String climate,
            @RequestParam (required = false) LocalDate departureDate,
            @RequestParam (required = false) Integer flexiDays,
            @RequestParam (required = false) String tag
            ) {
        return flightService.searchFlights(maxBudget, departureAirport, arrivalAirport, climate, departureDate, flexiDays, tag);
    }

    @GetMapping("/surpriseMe")
    public Flight findRandomFlight(@RequestParam String departureAirport) {

        try {
            Flight flight = flightService.findRandomFlight(departureAirport);
            return flight;

        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Flights found from departure airport: " + departureAirport, exception);
        }
    }
    

}
