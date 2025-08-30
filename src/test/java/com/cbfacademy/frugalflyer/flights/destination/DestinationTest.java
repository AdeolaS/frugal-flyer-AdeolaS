package com.cbfacademy.frugalflyer.flights.destination;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cbfacademy.frugalflyer.flights.airport.Airport;

@DisplayName(value = "Destination Test Suite")
public class DestinationTest {
    Destination destination;
    Airport heathrow;
    Airport gatwick;
    List<String> tagsList;
    List<Airport> airportsList;
    
    @BeforeEach
    public void setup() {
        heathrow = new Airport();
        gatwick = new Airport();
        airportsList = List.of(heathrow, gatwick);
        tagsList = List.of("multicultural", "city");
        destination = new Destination("London", "United Kingdom", "temperate", tagsList, airportsList);
    }

    @Test
    @DisplayName("getCountry retrieves the correct destination country.")
    public void testGetCountry() {
        assertEquals("United Kingdom", destination.getCountry());
    }

    @Test
    @DisplayName("getCity retrieves the correct destination city.")
    public void testGetity() {
        assertEquals("London", destination.getCity());
    }

    @Test
    @DisplayName("getClimate retrieves the correct destination climate.")
    public void testGetClimate() {
        assertEquals("temperate", destination.getClimate());
    }

    @Test
    @DisplayName("getTags retrieves the correct destination tags.")
    public void testGetTags() {
        assertEquals(tagsList, destination.getTags());
    }

    @Test
    @DisplayName("getAirport retrieves the correct destination airports.")
    public void testGetAirports() {
        assertEquals(airportsList, destination.getAirports());
    }
    
    @Test
    @DisplayName("setCountry sets the destination's country.")
    public void testSetCountry() {
        String country = "Spain";
        destination.setCountry(country);
        assertEquals(country, destination.getCountry());
    }

    @Test
    @DisplayName("setCity sets the destination's city.")
    public void testSetCity() {
        String city = "Barcelona";
        destination.setCity(city);
        assertEquals(city, destination.getCity());
    }

    @Test
    @DisplayName("setClimate sets the destination's climate.")
    public void testSetClimate() {
        String climate = "Tropical";
        destination.setClimate(climate);
        assertEquals(climate, destination.getClimate());
    }

    @Test
    @DisplayName("setTags sets the destination's tags.")
    public void testSetTags() {
        List<String> tags = List.of("Romantic", "Food");
        destination.setTags(tags);
        assertEquals(tags, destination.getTags());
    }

    @Test
    @DisplayName("setAirports sets the destination's airports.")
    public void testSetAirports() {
        Airport airport1 = new Airport();
        Airport airport2 = new Airport();
        List<Airport> airports = List.of(airport1, airport2);

        destination.setAirports(airports);
        assertEquals(airports, destination.getAirports());
    }
}
