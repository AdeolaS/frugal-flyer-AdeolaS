package com.cbfacademy.frugalflyer.flights.flight;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.cbfacademy.frugalflyer.flights.airport.Airport;
import com.cbfacademy.frugalflyer.flights.destination.Destination;
import com.cbfacademy.frugalflyer.flights.exceptions.InvalidDateException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

	@MockitoBean
	private FlightService flightService;

    private Destination destination = new Destination("City", "Country", "Climate", List.of("tag"), List.of());
    private Airport london = new Airport("LHR", "Heathrow Airport", destination);
    private Airport newYork = new Airport("JFK", "John F. Kennedy International Airport", destination);
    private Airport barcelona = new Airport("BCN", "Barcelona El Prat Airport", destination);
    private Flight flight = new Flight(london, newYork, 700, LocalDate.now());

    private List<Flight> defaultFlights = new ArrayList<>() {
        {
            add(new Flight(london, newYork, 700, LocalDate.now()));
            add(new Flight(london, barcelona, 149.99, LocalDate.now()));
            add(new Flight(london, barcelona, 199.99, LocalDate.now()));
        }
    };

    @Test
    @Description("GET /api/flights/search-via-airport returns 200 when departure airport is given")
    void test_searchFlightsUsingArrivalAirport_depatureAirportGiven() throws Exception {
        
        // Arrange
        when(flightService.searchFlightsUsingArrivalAirport(anyDouble(),anyString(),anyString(),any(),any())).thenReturn(defaultFlights);

        //perform() method will call the GET request, and the request
        this.mockMvc.perform(get("/api/flights/search-via-airport")
        .param("departureAirport", "LHR"))
        // and response will be printed
        .andDo(print())
        //assert that the response will be OK 200
        .andExpect(status().isOk());
    }

    // @Test
    // @Description("GET /api/flights/search-via-airport returns 400 when InvalidDateException is thrown")
    // void test_searchFlightsUsingArrivalAirport_InvalidDateException() throws Exception {
        
    //     // Arrange
    //     when(flightService.searchFlightsUsingArrivalAirport(anyDouble(),anyString(),anyString(),any(LocalDate.class),any(Integer.class))).thenThrow(new InvalidDateException(null));

    //     //perform() method will call the GET request, and the request
    //     this.mockMvc.perform(
    //     get("/api/flights/search-via-airport")
    //     .param("departureAirport", "LHR")
    //     .param("departureDate", "2024-01-01"))
    //     // and response will be printed
    //     .andDo(print())
    //     //assert that the response will be BAD_REQUEST 400
    //     .andExpect(status().isBadRequest());
    // }

    @Test
    @Description("GET /api/flights/search-via-climate-and-tags returns 200 when departure airport is given")
    void test_searchFlightsUsingClimateAndTags_depatureAirportGiven() throws Exception {
        
        // Arrange
        when(flightService.searchFlightsUsingClimateAndTags(anyDouble(),anyString(),anyString(),any(), any(), any())).thenReturn(defaultFlights);

        //perform() method will call the GET request, and the request
        this.mockMvc.perform(get("/api/flights/search-via-climate-and-tags")
        .param("departureAirport", "LHR"))
        //assert that the response will be OK 200
        .andExpect(status().isOk());
    }

    // @Test
    // @Description("GET /surprise-me returns 200 when departure airport is given")
    // void test_findRandomFlight_depatureAirportGiven() throws Exception {
        
    //     // Arrange
    //     when(flightService.findRandomFlight(anyString())).thenReturn(flight);

    //     //perform() method will call the GET request, and the request
    //     this.mockMvc.perform(get("/api/flights/surprise-me")
    //     .param("departureAirport", "LHR"))
    //     //assert that the response will be OK 200
    //     .andExpect(status().isOk());
    // }

    // @Test
    // @Description("GET /api/flights/cheap-flights returns 200 when airports and threshold is given")
    // void test_findCheapFlightAnomalies_AirportsAndThresholdGiven() throws Exception {
        
    //     // Arrange
    //     when(flightService.findCheapFlightAnomalies(anyString(), anyString(), anyDouble())).thenReturn(defaultFlights);

    //     //perform() method will call the GET request, and the request
    //     this.mockMvc.perform(get("/api/flights/cheap-flights")
    //     .param("departureAirport", "LHR")
    //     .param("arrivalAirport", "JFK")
    //     .param("threshold", "0.5"))
    //     //assert that the response will be OK 200
    //     .andExpect(status().isOk());
    // }

}
