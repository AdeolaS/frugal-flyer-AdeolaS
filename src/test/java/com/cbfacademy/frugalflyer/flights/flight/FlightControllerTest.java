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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

	@MockitoBean
	private FlightService flightService;

    private Airport london = new Airport();
    private Airport newYork = new Airport();
    private Airport barcelona = new Airport();

    private List<Flight> defaultFlights = new ArrayList<>() {
        {
            add(new Flight(london, newYork, 700, LocalDate.now()));
            add(new Flight(london, barcelona, 149.99, LocalDate.now()));
            add(new Flight(london, barcelona, 199.99, LocalDate.now()));
        }
    };

    @Test
    @Description("GET /api/flights/search-via-airport returns matching flights")
    void test_searchFlightsUsingArrivalAirport() throws Exception {
        // Arrange
        when(flightService.searchFlightsUsingArrivalAirport(anyDouble(),anyString(),anyString(),any(),any())).thenReturn(defaultFlights);

        this.mockMvc.perform(get("/search-via-airport")).andDo(print())
        .andExpect(status().isOk());
        //.andExpect(content().equals(defaultFlights));


        // this.mockMvc.perform(get("/search-via-airport")).andDo(print()).andExpect(status().isOk())
        // .andExpect(content().equals(defaultFlights));
        
        //(get("/search-via-airport")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Hello, Mock")));
    }

}


// @SpringBootTest(classes = FrugalflyerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// public class FlightControllerTest {
    
//     @LocalServerPort
// 	private int port;

// 	private URI baseURI;

// 	@Autowired
// 	private TestRestTemplate restTemplate;

//     private Airport london = new Airport();
//     private Airport newYork = new Airport();
//     private Airport barcelona = new Airport();

//     private List<Flight> defaultFlights = new ArrayList<>() {
//         {
//             add(new Flight(london, newYork, 700, LocalDate.now()));
//             add(new Flight(london, barcelona, 149.99, LocalDate.now()));
//             add(new Flight(london, barcelona, 199.99, LocalDate.now()));
//         }
//     };

//     @MockBean
//     private FlightService flightService;

//     @BeforeEach
// 	void setUp() throws RuntimeException {
// 		this.baseURI = UriComponentsBuilder.newInstance()
// 				.scheme("http")
// 				.host("localhost")
// 				.port(port)
// 				.path("api/flights")
// 				.build()
// 				.toUri();
// 	}

//     @Test
// 	@Description("GET /api/flights/search-via-airport returns matching flights")
// 	void test_searchFlightsUsingArrivalAirport() throws Exception {

//         // Arrange
//         when(flightService.searchFlightsUsingArrivalAirport(anyDouble(),anyString(),anyString(),any(),any())).thenReturn(defaultFlights);

// 		// Act
// 		ResponseEntity<List<Flight>> response = restTemplate.exchange(baseURI, HttpMethod.GET, null,
// 				new ParameterizedTypeReference<List<Flight>>() {}
//                 );

//         List<Flight> responseFlights = response.getBody();

// 		// Assert
// 		assertEquals(HttpStatus.OK, response.getStatusCode());
// 		assertNotNull(responseFlights);
// 		assertEquals(defaultFlights.size(), responseFlights.size());
// 		verify(flightService).searchFlightsUsingArrivalAirport(anyDouble(),anyString(),anyString(),any(),any());
// 	}
// }
