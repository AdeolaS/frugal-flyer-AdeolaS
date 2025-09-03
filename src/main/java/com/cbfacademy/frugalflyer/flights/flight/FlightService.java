package com.cbfacademy.frugalflyer.flights.flight;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.cbfacademy.frugalflyer.flights.airport.AirportRepository;
import com.cbfacademy.frugalflyer.flights.destination.DestinationRepository;
import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.AirportNotFoundException;
import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.FlightNotFoundException;
import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.InvalidClimateStringException;
import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.InvalidDateException;
import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.InvalidNumberException;
import com.cbfacademy.frugalflyer.flights.exceptions.customExceptions.InvalidTagStringException;
import com.cbfacademy.frugalflyer.flights.utility.FlightsUtilityClass;



/**
 * Service class to manage flight objects
 */
@Service
public class FlightService {
    
    private final FlightRepository flightRepo;
    private final AirportRepository airportRepo;
    private final DestinationRepository destinationRepo;

    private LocalDate earliestDate = null;
    private LocalDate latestDate = null;
    private LocalDate today = LocalDate.now();

    /**
     * List of flights that some methods will modify and return as the result of a search
     */
    private List<Flight> flights;

    private LocalDate[] dateRange;

    /**
    * Constructor for FlightService.
    *
    * @param flightRepo the FlightRepository to be used for data persistence
    * @param airportRepo the AirportRepository to be used for data persistence
    */
    public FlightService(FlightRepository flightRepo, AirportRepository airportRepo, DestinationRepository destinationRepo) {
        this.flightRepo = flightRepo;
        this.airportRepo = airportRepo;
        this.destinationRepo = destinationRepo;
    }

    /**
    * Retrieves flights, applying filters using given paramenters
    * @param maxBudget Maximum cost of flight
    * @param departureAirport Airport that the flight will depart from
    * @param arrivalAirport Airport that the flight will arrive at
    * @param departureDate Date that the flight will depart from the departure airport
    * @param flexiDays Number of flexible days to be subtracted and added from the departure date to allow a range of departure dates
    * @return List of flights that match the given search criteria
    */
    public List<Flight> searchFlightsUsingArrivalAirport(double maxBudget, String departureAirport, String arrivalAirport, LocalDate departureDate, Integer flexiDays) 
            throws InvalidDateException, AirportNotFoundException, InvalidNumberException {

        // Error handling that checks that input is valid.
        if (departureDate != null && today.isAfter(departureDate)) {
            throw new InvalidDateException("Departure date must be today's date or later.");
        }
        if (!airportRepo.existsById(departureAirport)) {
            throw new AirportNotFoundException("Invalid Airport code: " + departureAirport + ". Please insert an airport that is recognised by this application.");
        }
        if (arrivalAirport !=null && !airportRepo.existsById(arrivalAirport)) {
            throw new AirportNotFoundException("Invalid Airport code: " + arrivalAirport + ". Please insert an airport that is recognised by this application.");
        }
        if (maxBudget < 0.0) {
            throw new InvalidNumberException("The Maximum Budget cannot be a negative value.");
        }
        if (flexiDays !=null && flexiDays < 0.0) {
            throw new InvalidNumberException("The number of flexidays cannot be a negative value.");
        }

        //Set date range using helper function, then search repo for flights that satisfy search requirements.
        dateRange = FlightsUtilityClass.setDateRange(departureDate, flexiDays);
        earliestDate = dateRange[0];
        latestDate = dateRange[1];
        flights = flightRepo.searchFlightsUsingArrivalAirport(maxBudget, departureAirport, arrivalAirport, departureDate, earliestDate, latestDate);
        //A series of print messages that show the specified criteria
        System.out.println("\n------------------------------------");
        System.out.println("Criteria: ");
        System.out.println("Maximum Budget = " + maxBudget + ".");
        System.out.println("Departure Airport = " + departureAirport + ".");

        if (arrivalAirport != null) {
            System.out.println("Arrival Airport = " + arrivalAirport + ".");
        }
        if (departureDate != null && flexiDays== null) {
            System.out.println("Desired departure Date = " + departureDate + ".");
        }
        if (departureDate != null && flexiDays!= null) {
            System.out.println("Desired departure date range = " + earliestDate + " to " + latestDate + ".");
        }
        System.out.println("\n" + flights.size() + " flight(s) found.");
        System.out.println("------------------------------------");

        return flights;
    }

    /**
    * Retrieves flights, applying filters using given paramenters
    * @param maxBudget Maximum cost of flight
    * @param departureAirport Airport that the flight will depart from
    * @param climate Climate of the destination that the flight will arrive at
    * @param departureDate Date that the flight will depart from the departure airport
    * @param flexiDays Number of flexible days to be subtracted and added from the departure date to allow a range of departure dates
    * @param tag Descriptive tag that describes the type of holiday desired
    * @return List of flights that match the given search criteria
    */
    public List<Flight> searchFlightsUsingClimateAndTags(double maxBudget, String departureAirport, String climate, LocalDate departureDate, Integer flexiDays, String tag)
            throws InvalidDateException, InvalidNumberException, AirportNotFoundException, InvalidClimateStringException, InvalidTagStringException {
 
        // Error handling that checks that input is valid.
        if (departureDate != null && today.isAfter(departureDate)) {
            throw new InvalidDateException("Departure date must be today's date or later.");
        }
        if (!airportRepo.existsById(departureAirport)) {
            throw new AirportNotFoundException("Invalid Airport code: " + departureAirport + ". Please insert an airport that is recognised by this application.");
        }
        if (maxBudget < 0.0) {
            throw new InvalidNumberException("The Maximum Budget cannot be a negative value.");
        }
        if (flexiDays !=null && flexiDays < 0.0) {
            throw new InvalidNumberException("The number of flexidays cannot be a negative value.");
        }
        if (climate != null && !destinationRepo.existsByClimate(climate)) {
            throw new InvalidClimateStringException("Provided climate '" + climate + "'is not available. Please check the spelling, or input another climate type.");
        }
        if (tag != null && !destinationRepo.existsByTags(tag)) {
            throw new InvalidTagStringException("Provided tag '" + tag + "' is not available. Please check the spelling, or try inputting another tag.");
        }

        dateRange = FlightsUtilityClass.setDateRange(departureDate, flexiDays);
        earliestDate = dateRange[0];
        latestDate = dateRange[1];

        flights = flightRepo.searchFlightsUsingClimateAndTags(maxBudget, departureAirport, climate, departureDate, earliestDate, latestDate, tag);
        
        //A series of print messages that show to specified criteria
        System.out.println("\n------------------------------------");
        System.out.println("Criteria: ");
        System.out.println("Maximum Budget = " + maxBudget + ".");
        System.out.println("Departure Airport = " + departureAirport + ".");
        
        if (climate != null) {
            System.out.println("Desired Climate = " + climate + ".");
        }
        if (departureDate != null && flexiDays== null) {
            System.out.println("Desired departure Date = " + departureDate + ".");
        }
        if (departureDate != null && flexiDays!= null) {
            System.out.println("Desired departure date range = " + earliestDate + " to " + latestDate + ".");
        }
        if (tag != null) {
            System.out.println("Desired Destination Tag = " + tag + ".");
        }
        System.out.println("\n" + flights.size() + " flight(s) found.");
        System.out.println("------------------------------------");

        return flights;
    }
    
    /**
     * Finds a single random flight that leaves from the specified airport
     * @param departureAirport Airport that the flight will depart from
     * @return random flight
     */
    public Flight findRandomFlight(String departureAirport) throws AirportNotFoundException {

        if (!airportRepo.existsById(departureAirport)) {
            throw new AirportNotFoundException("Invalid Airport code: " + departureAirport + ". Please insert an airport that is recognised by this application.");
        }

        return flightRepo.findRandomFlight(departureAirport);
    }

    /**
     * Finds flight price anomalies using the z-score method
     * @param departureAirport IATA code of departure airport
     * @param arrivalAirport IATA code of arrival airport
     * @param threshold threshold to determine how high the zscore needs to be for a price to be classed as an anomaly
     * @return list of flights with price anomalies
     */
    public List<Flight> findCheapFlightAnomalies(String departureAirport, String arrivalAirport, double threshold)
        throws AirportNotFoundException, InvalidNumberException {

        if (!airportRepo.existsById(departureAirport)) {
            throw new AirportNotFoundException("Invalid Airport code: " + departureAirport + ". Please insert an airport that is recognised by this application.");
        }
        if (!airportRepo.existsById(arrivalAirport)) {
            throw new AirportNotFoundException("Invalid Airport code: " + arrivalAirport + ". Please insert an airport that is recognised by this application.");
        }
        if (threshold < 0.0) {
            throw new InvalidNumberException("The threshold cannot be a negative value.");
        }

        List<Double> prices = new ArrayList<>();

        List<Flight> flights = flightRepo.searchFlightsUsingAirportsOnly(departureAirport, arrivalAirport);

        if (flights.size() < 3) {
            System.out.println("\n------------------------------------");
            System.out.println("There are not enough flights between these two airports to find cheap flight data.");
            System.out.println("Returning all suitable flights.");
            System.out.println("------------------------------------");

            return flights;
        }

        List<Flight> cheapFlights = new ArrayList<>();

        double sumOfFlightPrices = 0.0;
        double sumOfDistanceToMeanSquared = 0.0;

        //calculate mean
        for (Flight flight : flights) {
            prices.add(flight.getPrice());
            sumOfFlightPrices += flight.getPrice();
        }
        double mean = sumOfFlightPrices / flights.size();

        //calculate standard deviation
        for (double price : prices) {
            sumOfDistanceToMeanSquared += Math.pow(mean-price, 2);
        }
        double standardDeviation = Math.sqrt(sumOfDistanceToMeanSquared/flights.size());

        //calculate zScore to be used to flag anomalies and add them to cheap flights list
        for (Flight flight : flights) {
            double zScore = (flight.getPrice() - mean) / standardDeviation;
            if (zScore < -threshold) {
                cheapFlights.add(flight);
            }
        }

        
        System.out.println("\n------------------------------------");
        System.out.println(flights.size() + " flight(s) found with matching departure and arrvial airports.");
        System.out.println("Average price of flights: " + mean);

        System.out.println(cheapFlights.size() + " cheap flights found.");
        System.out.println("------------------------------------");

        return cheapFlights;
    }

    public void deleteFlightbyId(Long id) throws FlightNotFoundException {
        
        if (flightRepo.findById(id).isPresent()) {
            flightRepo.deleteById(id);
        } else {
            throw new FlightNotFoundException("Invalid flight ID. Please enter an ID that exists in the database.");
        }
    }

    public Flight createNewFlight(Flight flight) throws OptimisticLockingFailureException {

        return flightRepo.save(flight);
    }

    public void updateFlight(Long id, Flight updatedFlight) throws FlightNotFoundException {

        if (!flightRepo.findById(id).isPresent()){
            throw new FlightNotFoundException("Invalid flight ID. Please enter an ID that exists in the database.");
        } else {
            Flight flight = flightRepo.findById(id).get();
            flight.setDepartureAirport(updatedFlight.getDepartureAirport());
            flight.setArrivalAirport(updatedFlight.getArrivalAirport());
            flight.setPrice(updatedFlight.getPrice());
            flight.setDepartureDate(updatedFlight.getDepartureDate());
        }
    }
}
