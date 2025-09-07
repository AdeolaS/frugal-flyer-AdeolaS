package com.cbfacademy.frugalflyer.flights.externalFlight;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Controller method that fetches the data from an external API.
 */
@RestController
@RequestMapping("/api/external-flights")
public class ExternalFlightController {
    
    private final ExternalFlightService externalFlightService;

    public ExternalFlightController(ExternalFlightService externalFlightService) {
        this.externalFlightService = externalFlightService;
    }

    @Operation(summary = "Retrieves external flight data from https://api.aviationstack.com/v1/flights, applying filters using given paramenters.")
    @GetMapping
    public ResponseEntity<ExternalFlightApiResponse> getExternalFlightData(
        @RequestParam String departureAirport, 
        @RequestParam String arrivalAirport
        ) throws Exception, IllegalArgumentException {

        String accessKey = "e4c48a8c4963d21a5e20e6c37b3c2043";
        try {
            return ResponseEntity
                .status(200)
                .body(externalFlightService.getExternalFlightData(departureAirport, arrivalAirport, accessKey));
        } catch(Exception e) {
            return ResponseEntity
                .status(500)
                .body(null);
        }
    }
}
