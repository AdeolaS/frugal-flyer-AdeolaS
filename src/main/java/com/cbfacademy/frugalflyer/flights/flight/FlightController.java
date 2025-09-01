package com.cbfacademy.frugalflyer.flights.flight;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.frugalflyer.flights.exceptions.ApiError;
import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.AirportNotFoundException;
import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.InvalidClimateStringException;
import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.InvalidDateException;
import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.InvalidNumberException;
import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.InvalidTagStringException;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class to implement flights API endpoints and handle requests.
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
    //Annotations for Swagger Documentation
    @Operation(summary = "Retrieves flights, applying filters using given paramenters.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flights successfully retrieved."),
        @ApiResponse(responseCode = "404", description = "Not Found - Input parameter was not found.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
        @ApiResponse(responseCode = "400", description = "Bad Request - Input parameter was invalid.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    //Endpoint
    @GetMapping("/search-via-airport")
    public List<Flight> searchFlightsUsingArrivalAirport(
            @RequestParam (defaultValue = "999999") double maxBudget,
            @RequestParam String departureAirport,
            @RequestParam (required = false) String arrivalAirport,
            @RequestParam (required = false) LocalDate departureDate,
            @RequestParam (required = false) Integer flexiDays
            ) throws InvalidDateException, AirportNotFoundException, InvalidNumberException {

        return flightService.searchFlightsUsingArrivalAirport(maxBudget, departureAirport, arrivalAirport, departureDate, flexiDays);
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
    //Annotations for Swagger Documentation
    @Operation(summary = "Retrieves flights, applying filters using given paramenters.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flights successfully retrieved."),
        @ApiResponse(responseCode = "404", description = "Not Found - Input parameter was not found.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
        @ApiResponse(responseCode = "400", description = "Bad Request - Input parameter was invalid.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    //Endpoint
    @GetMapping("/search-via-climate-and-tags")
    public List<Flight> searchFlightsUsingClimateAndTags(
            @RequestParam (defaultValue = "999999") double maxBudget,
            @RequestParam String departureAirport,
            @RequestParam (required = false) String climate,
            @RequestParam (required = false) LocalDate departureDate,
            @RequestParam (required = false) Integer flexiDays,
            @RequestParam (required = false) String tag
            ) throws InvalidDateException, AirportNotFoundException, InvalidNumberException, InvalidClimateStringException, InvalidTagStringException {

        return flightService.searchFlightsUsingClimateAndTags(maxBudget, departureAirport, climate, departureDate, flexiDays, tag);
    }

    /**
     * Finds a single random flight that leaves from the specified airport
     * @param departureAirport Airport that the flight will depart from
     * @return random flight
     */
    //Annotations for Swagger Documentation
    @Operation(summary = "Finds a single random flight that leaves from the specified airport.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flights successfully retrieved."),
        @ApiResponse(responseCode = "404", description = "Not Found - Input parameter was not found.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    //Endpoint
    @GetMapping("/surprise-me")
    public Flight findRandomFlight(@RequestParam String departureAirport) throws AirportNotFoundException {

        return flightService.findRandomFlight(departureAirport);
    }

    /**
     * Finds cheap flights, from and to specfied airports, with price anomalies
     * @param departureAirport Airport that the flight will depart from
     * @param arrivalAirport Airport that the flight will arrive at
     * @param threshold value to compare zScore with.
     * @return list of flights with price anomalies
     */
    //Annotations for Swagger Documentation
    @Operation(summary = "Finds cheap flights, from and to specfied airports, with price anomalies.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flights successfully retrieved."),
        @ApiResponse(responseCode = "404", description = "Not Found - Input parameter was not found.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
        @ApiResponse(responseCode = "400", description = "Bad Request - Input parameter was invalid.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    //Endpoint
    @GetMapping("/cheap-flights")
    public List<Flight> findCheapFlightAnomalies(
        @RequestParam String departureAirport, 
        @RequestParam String arrivalAirport,
        @RequestParam (defaultValue = "0.5") double threshold
        ) throws AirportNotFoundException, InvalidNumberException{
        
        return flightService.findCheapFlightAnomalies(departureAirport, arrivalAirport, threshold);
    }
    
}
