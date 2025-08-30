package com.cbfacademy.frugalflyer.flights.flight;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cbfacademy.frugalflyer.flights.airport.Airport;

@DisplayName(value = "Airport Test Suite")
public class FlightTest {

    private Airport departureAirport;
    private Airport arrivalAirport;
    private double price;
    private LocalDate departureDate;
    private Flight flight;
    
    @BeforeEach
    public void setup() {
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        price = 500.50;
        departureDate = LocalDate.of(2025, 9, 28);

        flight = new Flight(departureAirport, arrivalAirport, price, departureDate);
    }

    @Test
    @DisplayName("getDepartureAirport retrieves the correct departure airport.")
    public void testGetDepartureAirport() {
        assertEquals(departureAirport, flight.getDepartureAirport());
    }

    @Test
    @DisplayName("getArrivalAirport retrieves the correct arrival airport.")
    public void testGetArrivalAirport() {
        assertEquals(arrivalAirport, flight.getArrivalAirport());
    }

    @Test
    @DisplayName("getPrice retrieves the correct flight price.")
    public void testGetPrice() {
        assertEquals(price, flight.getPrice());
    }

    @Test
    @DisplayName("getDepartureDate retrieves the correct departure date.")
    public void testGetDepartureDate() {
        assertEquals(departureDate, flight.getDepartureDate());
    }

    @Test
    @DisplayName("setDepartureDate sets the flight's departure date.")
    public void testSetDepartureDate() {
        LocalDate newDepartureDate = LocalDate.of(2025, 10, 28);
        flight.setDepartureDate(newDepartureDate);
        assertEquals(newDepartureDate, flight.getDepartureDate());
    }

    @Test
    @DisplayName("setPrice sets the flight's price.")
    public void testSetPrice() {
        double newPrice = 700.75;
        flight.setPrice(newPrice);
        assertEquals(newPrice, flight.getPrice());
    }

    @Test
    @DisplayName("setDepartureAirport sets the flight's departure Airport.")
    public void testSetDepartureAirport() {
        Airport newDepartureAirport = new Airport();
        flight.setDepartureAirport(newDepartureAirport);
        assertEquals(newDepartureAirport, flight.getDepartureAirport());
    }

    @Test
    @DisplayName("setArrivalAirport sets the flight's arrival Airport.")
    public void testSetArrivslAirport() {
        Airport newArrivalAirport = new Airport();
        flight.setArrivalAirport(newArrivalAirport);
        assertEquals(newArrivalAirport, flight.getArrivalAirport());
    }
}
