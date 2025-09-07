package com.cbfacademy.frugalflyer.flights.airport;


import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.frugalflyer.flights.exceptions.ApiError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controller class to implement airport API endpoints and handle requests.
 */
@RestController
@RequestMapping("/api/airports")
public class AirportController {
    
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    /**
     * Deletes specified airport
     * @param code Iata code of airport
     * @return reponse body
     * @throws AirportNotFoundException if no airports exist with airport code
     * @throws AirportInUseException if specifed airport's key is being used somewhere else in the database.
     */
    //Annotations for Swagger Documentation
    @Operation(summary = "Deletes an airport, with the specified code, from the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Flight successfully deleted."),
        @ApiResponse(responseCode = "404", description = "Not Found - Input Airport code was not found.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
        @ApiResponse(responseCode = "400", description = "Airport in Use - Input airport has flight going to and from it so cannot be deleted.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    //Endpoint
    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteAirportByCode(@PathVariable String code) throws AirportNotFoundException, AirportInUseException {
        
        airportService.deleteAirportByCode(code);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    
    /**
     * Add a new airport
     * @param airport Airport object to add
     * @return Response entity with body of the created airport
     * @throws IllegalArgumentException 
     * @throws OptimisticLockingFailureException
     */
    //Annotations for Swagger Documentation
    @Operation(summary = "Adds a new airport to the database.")   
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flight successfully created."),
        @ApiResponse(responseCode = "404", description = "Invalid Argument.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
        @ApiResponse(responseCode = "412", description = "Optimistic Locking Failure Exception.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })  
    @PostMapping
    public ResponseEntity<Airport> createNewAirport(
        @RequestParam String airportCode, 
        @RequestParam String airportName, 
        @RequestParam Long destinationId) throws IllegalArgumentException, OptimisticLockingFailureException {

        Airport createdAirport = airportService.createNewAirport(airportCode, airportName, destinationId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdAirport);
    }

    /**
     * Updates an existing airport. The IATA code will be kept the same.
     * @param code IATA code of existing airport
     * @param updatedAirport new Airport
     * @return updated airport
     * @throws AirportNotFoundException 
     * @throws OptimisticLockingFailureException
     * @throws IllegalArgumentException
     */
    //Annotations for Swagger Documentation
    @Operation(summary = "Updates an airport in the database.")   
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flight successfully updated."),
        @ApiResponse(responseCode = "404", description = "Invalid Argument.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
        @ApiResponse(responseCode = "412", description = "Optimistic Locking Failure Exception.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })  
    @PutMapping
    public Airport updateAirport(
        @RequestParam String oldAirportCode, 
        @RequestParam String airportName, 
        @RequestParam Long destinationId
    ) throws AirportNotFoundException, OptimisticLockingFailureException, IllegalArgumentException {

        return airportService.updateAirport(oldAirportCode, airportName, destinationId);
    }


    /**
     * Retrieves all airports in the database.
     * @return list of airports
     */
    //Annotations for Swagger Documentation
    @Operation(summary = "Retrieves all airports in the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flight successfully updated."),
        @ApiResponse(responseCode = "404", description = "Airport Not Found - The code you gave doesn't belong to an airport in the database.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })    //Endpoint
    @GetMapping
    public List<Airport> getAllAirports() {
        return airportService.getAllAirports();
    }

    /**
     * Finds an airport, with the specified code, from the database.
     * @param code IATA code of airport
     * @return the airport
     * @throws AirportNotFoundException
     */
    //Annotations for Swagger Documentation
    @Operation(summary = "Finds an airport, with the specified code, from the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flight successfully found."),
        @ApiResponse(responseCode = "404", description = "Not Found - Input parameter was not found.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    //Endpoint
    @GetMapping("/{code}")
    public Airport getAirportByCode(@PathVariable String code) throws AirportNotFoundException {

        return airportService.getAirportByCode(code);
    }
}
