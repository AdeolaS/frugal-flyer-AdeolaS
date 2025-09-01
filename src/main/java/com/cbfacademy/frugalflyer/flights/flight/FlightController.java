package com.cbfacademy.frugalflyer.flights.flight;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cbfacademy.frugalflyer.flights.customExceptions.AirportNotFoundException;
import com.cbfacademy.frugalflyer.flights.customExceptions.InvalidClimateStringException;
import com.cbfacademy.frugalflyer.flights.customExceptions.InvalidDateException;
import com.cbfacademy.frugalflyer.flights.customExceptions.InvalidNumberException;
import com.cbfacademy.frugalflyer.flights.customExceptions.InvalidTagStringException;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class to implement flights API endpoints.
 */
@RestController
@OpenAPIDefinition(info = @Info(
    title = "Frugal Flyer API",
    description = "Helps you find flights to the places you want to go for prices that won't empty your pocket."))
@RequestMapping("/api/flights")
public class FlightController {
    
    private final FlightService flightService;

    /**
     * Contructor for FlightController
     * @param flightService Service class that handles business logic
     */
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    /**
    * Retrieves flights, applying filters using given paramenters
    * @param maxBudget Maximum cost of flight
    * @param departureAirport Airport that the flight will depart from
    * @param arrivalAirport Airport that the flight will arrive at
    * @param departureDate Date that the flight will depart from the departure airport
    * @param flexiDays Number of flexible days to be subtracted and added from the departure date to allow a range of departure dates
    * @return List of flights that match the given search criteria
    */
    @Operation(summary = "Retrieves flights, applying filters using given paramenters.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flights successfully retrieved."),
        @ApiResponse(responseCode = "404", description = "Not Found - Input parameter was not found."),
        @ApiResponse(responseCode = "400", description = "Bad Request - Input parameter was invalid.")
    })
    @GetMapping("/search-via-airport")
    public List<Flight> searchFlightsUsingArrivalAirport(
            @RequestParam (defaultValue = "999999") double maxBudget,
            @RequestParam String departureAirport,
            @RequestParam (required = false) String arrivalAirport,
            @RequestParam (required = false) LocalDate departureDate,
            @RequestParam (required = false) Integer flexiDays
            ) {

        try {
            return flightService.searchFlightsUsingArrivalAirport(maxBudget, departureAirport, arrivalAirport, departureDate, flexiDays);
        } catch(InvalidDateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch(AirportNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(InvalidNumberException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
    * Retrieves flights, applying filters using given paramenters
    * @param maxBudget Maximum cost of flight
    * @param departureAirport Airport that the flight will depart from
    * @param climate Climate of the destination that the flight will arrive at
    * @param departureDate Date that the flight will depart from the departure airport
    * @param flexiDays Number of flexible days to be subtracted and added from the departure date to allow a range of departure dates
    * @param tag Descriptive tag that describes the type of holiday desired
    * @return List of flights that match the given search criteria
    */
    @Operation(summary = "Retrieves flights, applying filters using given paramenters.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flights successfully retrieved."),
        @ApiResponse(responseCode = "404", description = "Not Found - Input parameter was not found."),
        @ApiResponse(responseCode = "400", description = "Bad Request - Input parameter was invalid.")
    })
    @GetMapping("/search-via-climate-and-tags")
    public List<Flight> searchFlightsUsingClimateAndTags(
            @RequestParam (defaultValue = "999999") double maxBudget,
            @RequestParam String departureAirport,
            @RequestParam (required = false) String climate,
            @RequestParam (required = false) LocalDate departureDate,
            @RequestParam (required = false) Integer flexiDays,
            @RequestParam (required = false) String tag
            ) {
        
        try {
            return flightService.searchFlightsUsingClimateAndTags(maxBudget, departureAirport, climate, departureDate, flexiDays, tag);
        } catch(InvalidDateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch(AirportNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(InvalidNumberException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch(InvalidClimateStringException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(InvalidTagStringException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Finds a single random flight that leaves from the specified airport
     * @param departureAirport Airport that the flight will depart from
     * @return random flight
     */
    @Operation(summary = "Finds a single random flight that leaves from the specified airport.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flights successfully retrieved."),
        @ApiResponse(responseCode = "404", description = "Not Found - Input parameter was not found.")
    })
    @GetMapping("/surprise-me")
    public Flight findRandomFlight(@RequestParam String departureAirport) {

        try {
            return flightService.findRandomFlight(departureAirport);
        } catch (AirportNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Finds cheap flights, from and to specfied airports, with price anomalies
     * @param departureAirport Airport that the flight will depart from
     * @param arrivalAirport Airport that the flight will arrive at
     * @param threshold value to compare zScore with.
     * @return list of flights with price anomalies
     */
    @Operation(summary = "Finds cheap flights, from and to specfied airports, with price anomalies.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flights successfully retrieved."),
        @ApiResponse(responseCode = "404", description = "Not Found - Input parameter was not found."),
        @ApiResponse(responseCode = "400", description = "Bad Request - Input parameter was invalid.")
    })
    @GetMapping("/cheap-flights")
    public List<Flight> findCheapFlightAnomalies(
        @RequestParam String departureAirport, 
        @RequestParam String arrivalAirport,
        @RequestParam (defaultValue = "0.5") double threshold
        ) {
        
        try{
            return flightService.findCheapFlightAnomalies(departureAirport, arrivalAirport, threshold);
        } catch(AirportNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(InvalidNumberException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    

}
