package com.cbfacademy.frugalflyer.flights.airport;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cbfacademy.frugalflyer.flights.destination.Destination;

@DisplayName(value = "Airport Test Suite")
public class AirportTest {
    private Airport airport;
    Destination london;

    @BeforeEach
    public void setup() {
        london = new Destination();
        airport = new Airport("LHR", "London Heathrow Airpport", london);
    }
    
    @Test
    @DisplayName("getCode retrieves the correct airport code.")
    public void testGetCode() {
        assertEquals("LHR", airport.getCode());
    }

    @Test
    @DisplayName("getName retrieves the correct airport name.")
    public void testGetName() {
        assertEquals("London Heathrow Airpport", airport.getName());
    }

    @Test
    @DisplayName("getDestination retrieves the correct airport destination.")
    public void testGetDestination() {
        assertEquals(london, airport.getDestination());
    }
    
    @Test
    @DisplayName("setCode sets the airport's code.")
    public void testSetCode() {
        String code = "BCN";
        airport.setCode(code);
        assertEquals(code, airport.getCode());
    }

    @Test
    @DisplayName("setName sets the airport's name.")
    public void testSetName() {
        String name = "Barcelona-El Prat Airport";
        airport.setName(name);
        assertEquals(name, airport.getName());
    }

    @Test
    @DisplayName("setDestination sets the airport's destination.")
    public void testSetDestination() {
        Destination destination = new Destination();
        airport.setDestination(destination);
        assertEquals(destination, airport.getDestination());
    }
}
