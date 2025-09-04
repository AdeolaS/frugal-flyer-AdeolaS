package com.cbfacademy.frugalflyer.flights.flight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cbfacademy.frugalflyer.flights.airport.AirportNotFoundException;
import com.cbfacademy.frugalflyer.flights.airport.AirportRepository;
import com.cbfacademy.frugalflyer.flights.destination.DestinationRepository;
import com.cbfacademy.frugalflyer.flights.exceptions.InvalidClimateStringException;
import com.cbfacademy.frugalflyer.flights.exceptions.InvalidDateException;
import com.cbfacademy.frugalflyer.flights.exceptions.InvalidNumberException;
import com.cbfacademy.frugalflyer.flights.exceptions.InvalidTagStringException;

@DisplayName(value = "Flight Service Test Suite")
public class FlightServiceTest {
    AutoCloseable openMocks;

    @Mock
    private FlightRepository flightRepo;

    @Mock
    private AirportRepository airportRepo;

    @Mock
    private DestinationRepository destinationRepo;

    private FlightService flightService;

    private final LocalDate today = LocalDate.now();

    @BeforeEach
    public void setUp() {
        // creates mock objects for all variables annoted with @mock in this class
        openMocks = MockitoAnnotations.openMocks(this);
        flightService = new FlightService(flightRepo, airportRepo, destinationRepo); 
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }


    // ---------------------------------------------------------------------------------------------------------
    // TESTS FOR searchFlightsUsingArrivalAirport() ------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------------------


    @Test
    @DisplayName("searchFlightsUsingArrivalAirport throws InvalidDateException when departure date is in the past")
    public void test_searchUsingArrivalAirport_throwsInvalidDate() {

        LocalDate pastDate = today.minusDays(1);

        assertThrows(InvalidDateException.class, () ->
            flightService.searchFlightsUsingArrivalAirport(500.0, "LHR", "JFK", pastDate, 2));
    }

    @Test 
    @DisplayName("searchFlightsUsingArrivalAirport throws AirportNotFoundException when departure airport doesn't exist")
    void test_searchUsingArrivalAirport_throwsAirportNotFoundDeparture() {

        // Mockito stubbing statement.
        when(airportRepo.existsById("LHR")).thenReturn(false);

        assertThrows(AirportNotFoundException.class, () ->
            flightService.searchFlightsUsingArrivalAirport(500.0, "LHR", "JFK", today.plusDays(1), 2)
        );
    }

    @Test 
    @DisplayName("searchFlightsUsingArrivalAirport throws AirportNotFoundException when arrival airport doesn't exist")
    void test_searchUsingArrivalAirport_throwsAirportNotFoundArrival() {

        // Mockito stubbing statement.
        when(airportRepo.existsById("JFK")).thenReturn(false);

        assertThrows(AirportNotFoundException.class, () ->
            flightService.searchFlightsUsingArrivalAirport(500.0, "LHR", "JFK", today.plusDays(1), 2)
        );
    }

    @Test 
    @DisplayName("searchFlightsUsingArrivalAirport throws InvalidNumberException when budget is less than zero")
    void test_searchUsingArrivalAirport_throwsInvalidNumberBudget() {

        // Mockito stubbing statements needed here, otherwise Mockito while return false 
        // on the boolean checks that come before the one being tested.
        when(airportRepo.existsById("JFK")).thenReturn(true);
        when(airportRepo.existsById("LHR")).thenReturn(true);

        assertThrows(InvalidNumberException.class, () ->
            flightService.searchFlightsUsingArrivalAirport(-500.0, "LHR", "JFK", today.plusDays(1), 2)
        );
    }

    @Test 
    @DisplayName("searchFlightsUsingArrivalAirport throws InvalidNumberException when flexidays is less than zero")
    void test_searchUsingArrivalAirport_throwsInvalidNumberFlexidays() {

        // Mockito stubbing statements needed here, otherwise Mockito while return false 
        // on the boolean checks that come before the one being tested.
        when(airportRepo.existsById("JFK")).thenReturn(true);
        when(airportRepo.existsById("LHR")).thenReturn(true);

        assertThrows(InvalidNumberException.class, () ->
            flightService.searchFlightsUsingArrivalAirport(500.0, "LHR", "JFK", today.plusDays(1), -2)
        );
    }

    @Test
    @DisplayName("searchFlightsUsingArrivalAirport works when arrival airport is null")
    void test_searchUsingArrivalAirport_worksWhenArrivalAirportIsNull() throws Exception{

        //create mock flights
        Flight mockFlight = new Flight();
        Flight mockFlight2 = new Flight();
        Flight mockFlight3 = new Flight();

        //create stub to prevent from defaulting to false and throwing invalid airport exception
        when(airportRepo.existsById("LHR")).thenReturn(true);

        //Stub creation. Arrival apart is null. Other parameters can be any value.
        when(flightRepo.searchFlightsUsingArrivalAirport(
                anyDouble(), anyString(), isNull(), any(), any(), any()
        )).thenReturn(List.of(mockFlight, mockFlight2, mockFlight3));

        //Searches for the three mock flights
        List<Flight> result = flightService.searchFlightsUsingArrivalAirport(
            500.0, "LHR", null, today.plusDays(3), 1
        );

        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("searchFlightsUsingArrivalAirport returns flights when inputs are valid")
    void test_searchUsingArrivalAirport_returnsFlightsWhenInputsAreValid() throws Exception{

        //create mock flights
        Flight mockFlight = new Flight();
        Flight mockFlight2 = new Flight();
        Flight mockFlight3 = new Flight();

        //create stub to stop Mockito from defaulting to false and throwing invalid airport exception
        when(airportRepo.existsById("LHR")).thenReturn(true);
        when(airportRepo.existsById("JFK")).thenReturn(true);

        //Stub creation. Parameters can be any value.
        when(flightRepo.searchFlightsUsingArrivalAirport(
                anyDouble(), anyString(), anyString(), any(), any(), any()
        )).thenReturn(List.of(mockFlight, mockFlight2, mockFlight3));

        //Searches for the three mock flights
        List<Flight> result = flightService.searchFlightsUsingArrivalAirport(
            500.0, "LHR", "JFK", today.plusDays(3), 1
        );

        assertEquals(3, result.size());
    }


    // ---------------------------------------------------------------------------------------------------------
    // TESTS FOR searchFlightsUsingClimateAndTags() ------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------------------


    @Test
    @DisplayName("searchFlightsUsingClimateAndTags returns flights when inputs are valid")
    void test_searchUsingClimateAndTags_returnsFlightsWhenInputsAreValid() throws Exception{

        //create mock flights
        Flight mockFlight = new Flight();
        Flight mockFlight2 = new Flight();
        Flight mockFlight3 = new Flight();

        //create stub to stop Mockito from defaulting to false and throwing exceptions
        when(airportRepo.existsById("LHR")).thenReturn(true);
        when(destinationRepo.existsByClimate("Tropical")).thenReturn(true);
        when(destinationRepo.existsByTags("cosmopolitan")).thenReturn(true);

        //Stub creation. Parameters can be any value.
        when(flightRepo.searchFlightsUsingClimateAndTags(
                anyDouble(), anyString(), anyString(), any(), any(), any(), any()
        )).thenReturn(List.of(mockFlight, mockFlight2, mockFlight3));

        //Searches for the three mock flights
        List<Flight> result = flightService.searchFlightsUsingClimateAndTags(
            500.0, "LHR", "Tropical", today.plusDays(3), 1, "cosmopolitan"
        );

        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("searchFlightsUsingClimateAndTags throws InvalidDateException when departure date is in the past")
    public void test_searchUsingClimateAndTags_throwsInvalidDate() {

        LocalDate pastDate = today.minusDays(1);

        assertThrows(InvalidDateException.class, () ->
            flightService.searchFlightsUsingClimateAndTags(500.0, "LHR", "Tropical", pastDate, 2, "cosmopolitan"));
    }

    @Test 
    @DisplayName("searchFlightsUsingClimateAndTags throws AirportNotFoundException when departure airport doesn't exist")
    void test_searchUsingClimateAndTags_throwsAirportNotFoundDeparture() {

        // Mockito stubbing statement.
        when(airportRepo.existsById("LHR")).thenReturn(false);

        assertThrows(AirportNotFoundException.class, () ->
            flightService.searchFlightsUsingClimateAndTags(500.0, "LHR", "Tropical", today.plusDays(1), 2, "cosmopolitan"));
    }

    @Test 
    @DisplayName("searchFlightsUsingClimateAndTags throws InvalidNumberException when budget is less than zero")
    void test_searchUsingClimateAndTags_throwsInvalidNumberBudget() {

        // Mockito stubbing statements needed here, otherwise Mockito while return false 
        // on the boolean checks that come before the one being tested.
        when(airportRepo.existsById("LHR")).thenReturn(true);
        when(destinationRepo.existsByClimate("Tropical")).thenReturn(true);
        when(destinationRepo.existsByTags("cosmopolitan")).thenReturn(true);

        assertThrows(InvalidNumberException.class, () ->
            flightService.searchFlightsUsingClimateAndTags(-500.0, "LHR", "Tropical", today.plusDays(3), 1, "cosmopolitan")
        );
    }

    @Test 
    @DisplayName("searchFlightsUsingClimateAndTags throws InvalidNumberException when flexidays is less than zero")
    void test_searchUsingClimateAndTags_throwsInvalidNumberFlexidays() {

        // Mockito stubbing statements needed here, otherwise Mockito while return false 
        // on the boolean checks that come before the one being tested.
        when(airportRepo.existsById("LHR")).thenReturn(true);
        when(destinationRepo.existsByClimate("Tropical")).thenReturn(true);
        when(destinationRepo.existsByTags("cosmopolitan")).thenReturn(true);

        assertThrows(InvalidNumberException.class, () ->
            flightService.searchFlightsUsingClimateAndTags(500.0, "LHR", "Tropical", today.plusDays(3), -1, "cosmopolitan")
        );
    }

    @Test 
    @DisplayName("searchFlightsUsingClimateAndTags throws InvalidClimateStringException when climate string does not exist")
    void test_searchUsingClimateAndTags_throwsInvalidClimateString() {

        // Mockito stubbing statements needed here, otherwise Mockito while return false 
        // on the boolean checks that come before the one being tested.
        when(airportRepo.existsById("LHR")).thenReturn(true);
        when(destinationRepo.existsByClimate("Tropical")).thenReturn(false);
        when(destinationRepo.existsByTags("cosmopolitan")).thenReturn(true);

        assertThrows(InvalidClimateStringException.class, () ->
            flightService.searchFlightsUsingClimateAndTags(500.0, "LHR", "Tropical", today.plusDays(3), 1, "cosmopolitan")
        );
    }

    @Test 
    @DisplayName("searchFlightsUsingClimateAndTags throws InvalidTagStringException when tag string does not exist")
    void test_searchUsingClimateAndTags_throwsInvalidTagString() {

        // Mockito stubbing statements needed here, otherwise Mockito while return false 
        // on the boolean checks that come before the one being tested.
        when(airportRepo.existsById("LHR")).thenReturn(true);
        when(destinationRepo.existsByClimate("Tropical")).thenReturn(true);
        when(destinationRepo.existsByTags("cosmopolitan")).thenReturn(false);

        assertThrows(InvalidTagStringException.class, () ->
            flightService.searchFlightsUsingClimateAndTags(500.0, "LHR", "Tropical", today.plusDays(3), 1, "cosmopolitan")
        );
    }

    // ---------------------------------------------------------------------------------------------------------
    // TESTS FOR findRandomFlight() ----------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------------------

    @Test 
    @DisplayName("findRandomFlight throws AirportNotFoundException when departure airport doesn't exist")
    void test_findRandomFlight_throwsAirportNotFound() {

        // Mockito stubbing statement.
        when(airportRepo.existsById("LHR")).thenReturn(false);

        assertThrows(AirportNotFoundException.class, () -> flightService.findRandomFlight("LHR"));
    }

    @Test
    @DisplayName("findRandomFlight returns a single flight when input is valid")
    void test_findRandomFlight_returnsFlightWhenInputIsValid() throws Exception{

        //create mock flights
        Flight mockFlight = new Flight();

        //create stub to stop Mockito from defaulting to false and throwing exceptions
        when(airportRepo.existsById("LHR")).thenReturn(true);

        //Stub creation. Parameters can be any value.
        when(flightRepo.findRandomFlight(anyString())).thenReturn(mockFlight);

        //Searches for the three mock flights
        Flight result = flightService.findRandomFlight("LHR");

        assertEquals(mockFlight, result);
    }
 
    // ---------------------------------------------------------------------------------------------------------
    // TESTS FOR findCheapFlightAnomalies() --------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------------------

    @Test 
    @DisplayName("findCheapFlightAnomalies throws AirportNotFoundException when departure airport doesn't exist")
    void test_findCheapFlightAnomalies_throwsAirportNotFoundDeparture() {

        // Mockito stubbing statement.
        when(airportRepo.existsById("LHR")).thenReturn(false);

        assertThrows(AirportNotFoundException.class, () ->
            flightService.findCheapFlightAnomalies("LHR", "JFK", 2));
    }

    @Test 
    @DisplayName("findCheapFlightAnomalies throws AirportNotFoundException when arrival airport doesn't exist")
    void test_findCheapFlightAnomalies_throwsAirportNotFoundArrival() {

        // Mockito stubbing statement.
        when(airportRepo.existsById("JFK")).thenReturn(false);
        when(airportRepo.existsById("LHR")).thenReturn(true);

        assertThrows(AirportNotFoundException.class, () ->
            flightService.findCheapFlightAnomalies("LHR", "JFK", 2));
    }

    @Test 
    @DisplayName("findCheapFlightAnomalies throws InvalidNumberException when threshold is less than zero")
    void test_findCheapFlightAnomalies_throwsInvalidNumberBudget() {

        // Mockito stubbing statements needed here, otherwise Mockito while return false 
        // on the boolean checks that come before the one being tested.
        when(airportRepo.existsById("JFK")).thenReturn(true);
        when(airportRepo.existsById("LHR")).thenReturn(true);

        assertThrows(InvalidNumberException.class, () ->
            flightService.findCheapFlightAnomalies("LHR", "JFK", -2));
    }

    @Test 
    @DisplayName("findCheapFlightAnomalies returns all flights when there are fewer than 3 flights")
    void test_findCheapFlightAnomalies_FewerThanThreeFlightsReturnsAll() throws Exception {

        // Mockito stub statements to pass exception checks
        when(airportRepo.existsById(anyString())).thenReturn(true);

        //Create two flights with prices
        Flight flight = new Flight();
        Flight flight2 = new Flight();

        flight.setPrice(100.02);
        flight2.setPrice(200.30);
        List<Flight> flights = List.of(flight, flight2);

        when(flightRepo.searchFlightsUsingAirportsOnly("LHR", "JFK")).thenReturn(flights);

        List<Flight> result = flightService.findCheapFlightAnomalies("LHR", "JFK", 1.5);

        assertEquals(flights, result, "Should return all flights when fewer than 3 available");
    }

    @ParameterizedTest
    @DisplayName("findCheapFlightAnomalies returns anomalous flight from a set of flights ")
    @CsvSource({
        "450.02, 550.30, 530.20, 181.30",
        "181.21, 178.2, 161.20, 80.10",
        "1810.21, 1780.2, 3561.20, 1000.10",
        "810.21, 780.2, 961.20, 600.10"
    })
    void testCheapFlightAnomaliesDetected(double price1, double price2, double price3, double priceAnomaly) throws Exception {

        // Mockito stub statements to pass exception checks
        when(airportRepo.existsById(anyString())).thenReturn(true);

        //Create four sample flights with prices
        Flight flight = new Flight();
        Flight flight2 = new Flight();
        Flight flight3 = new Flight();
        Flight flight4 = new Flight();

        flight.setPrice(price1); //First three flights should not be returned
        flight2.setPrice(price2);
        flight3.setPrice(price3);
        flight4.setPrice(priceAnomaly); //This one should be flagged for being price anomaly


        List<Flight> flights = List.of(flight, flight2, flight3, flight4);
        when(flightRepo.searchFlightsUsingAirportsOnly("LHR", "JFK")).thenReturn(flights);

        List<Flight> result = flightService.findCheapFlightAnomalies("LHR", "JFK", 0.5);

        assertTrue(result.contains(flight4), "Cheap outlier flight should be flagged");
        assertFalse(result.contains(flight2), "Normal flights should not be flagged");
        assertEquals(1, result.size(), "Only one anomaly expected");
    }
}

