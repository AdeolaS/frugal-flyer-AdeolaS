package com.cbfacademy.frugalflyer.flights.flight;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import com.cbfacademy.frugalflyer.flights.airport.Airport;

public class FlightServiceTest {
    
    private FlightService flightService;
    private FlightRepository flightRepo;
    private Flight flight1, flight2, flight3;
    private Airport depatureAirport1, depatureAirport2;
    private Airport arrivalAirport1, arrivalAirport2;

    @BeforeEach
    public void setUp() {
        flightRepo = Mockito.mock(FlightRepository.class);
        flightService = new FlightService(flightRepo);

        depatureAirport1 = new Airport();
        depatureAirport2 = new Airport();
        arrivalAirport1 = new Airport();
        arrivalAirport2 = new Airport();

        flight1 = new Flight(depatureAirport1, arrivalAirport1, 250.49, LocalDate.of(2026, 02, 25));
        flight1 = new Flight(depatureAirport2, arrivalAirport2, 499.99, LocalDate.of(2026, 01, 28));
    }

}
