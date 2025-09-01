package com.cbfacademy.frugalflyer.flights.externalFlight;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/flights")
public class ExternalFlightController {
    
    private final ExternalFlightService externalFlightDataService;

    
}
