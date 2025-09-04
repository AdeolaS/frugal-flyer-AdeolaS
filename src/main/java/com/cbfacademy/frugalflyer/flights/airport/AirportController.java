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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cbfacademy.frugalflyer.flights.exceptions.ApiError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/airports")
public class AirportController {
    
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    //Annotations for Swagger Documentation
    @Operation(summary = "Deletes an airport, with the specified code, from the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Flights successfully deleted."),
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
    
    //Annotations for Swagger Documentation
    @Operation(summary = "Adds a new airport to the database.")   
    @ApiResponse(responseCode = "200", description = "Flights successfully retrieved.")   
    @PostMapping
    public ResponseEntity<Airport> createNewAirport(@RequestBody Airport airport) throws IllegalArgumentException, OptimisticLockingFailureException {

        try {
            Airport createdAirport = airportService.createNewAirport(airport);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdAirport);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        }
    }

    //Annotations for Swagger Documentation
    @Operation(summary = "Updates an airport in the database.")
    @ApiResponse(responseCode = "200", description = "Flight successfully updated.") 
    @PutMapping("/{code}")
    public Airport updateAirport(
        @PathVariable String code,
        @RequestBody Airport updatedAirport
    ) throws AirportNotFoundException {

        return airportService.updateAirport(code, updatedAirport);
    }


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
