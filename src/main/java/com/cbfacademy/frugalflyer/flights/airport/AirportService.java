package com.cbfacademy.frugalflyer.flights.airport;

import org.springframework.stereotype.Service;


@Service
public class AirportService {
    
    private final AirportRepository airportRepo;

    public AirportService(AirportRepository airportRepo) {
        this.airportRepo = airportRepo;
    }

    public Airport findAirportById(String id) {
        return airportRepo.findById(id.toUpperCase()).orElseThrow();
    }
}
