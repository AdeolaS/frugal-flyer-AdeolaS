package com.cbfacademy.frugalflyer.flights.flight;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.cbfacademy.frugalflyer.flights.airport.Airport;
import com.cbfacademy.frugalflyer.flights.airport.AirportRepository;

public class FlightServiceTest {
    
    private FlightService flightService;
    private FlightRepository flightRepo;
    private AirportRepository airportRepo;
    private Flight flight1, flight2, flight3;
    private Airport depatureAirport1, depatureAirport2;
    private Airport arrivalAirport1, arrivalAirport2;

    @BeforeEach
    public void setUp() {
        // flightRepo = Mockito.mock(FlightRepository.class);
        // flightService = new FlightService(flightRepo, airportRepo);

        // depatureAirport1 = new Airport();
        // depatureAirport2 = new Airport();
        // arrivalAirport1 = new Airport();
        // arrivalAirport2 = new Airport();

        // flight1 = new Flight(depatureAirport1, arrivalAirport1, 250.49, LocalDate.of(2026, 02, 25));
        // flight2 = new Flight(depatureAirport2, arrivalAirport2, 499.99, LocalDate.of(2026, 01, 28));
        // flight3 = new Flight(depatureAirport1, arrivalAirport2, 99.99, LocalDate.of(2026, 03, 8));

        
    }

    @Test
    public void testSearchFlightsUsingArrivalAirport() {

    }

}
