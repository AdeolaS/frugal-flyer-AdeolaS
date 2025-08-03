package com.cbfacademy.frugalflyer.flights.destination;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DestinationService {
    
    private final DestinationRepository destinationRepo;

    public DestinationService(DestinationRepository destinationRepo) {
        this.destinationRepo = destinationRepo;
    }

    List<Destination> findByClimate(String climate) {
        return destinationRepo.findByClimate(climate);
    }
}
