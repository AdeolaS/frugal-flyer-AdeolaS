package com.cbfacademy.frugalflyer.flights.flight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.cbfacademy.frugalflyer.flights.airport.AirportRepository;
import com.cbfacademy.frugalflyer.flights.customExceptions.AirportNotFoundException;
import com.cbfacademy.frugalflyer.flights.customExceptions.InvalidDateException;
import com.cbfacademy.frugalflyer.flights.customExceptions.InvalidNumberException;
import com.cbfacademy.frugalflyer.flights.destination.DestinationRepository;

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

}
