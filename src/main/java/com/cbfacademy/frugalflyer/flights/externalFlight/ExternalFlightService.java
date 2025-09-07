package com.cbfacademy.frugalflyer.flights.externalFlight;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.Scanner;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.HttpURLConnection;

/**
 * This class deals with fetching flights from an external API (Aviation Stack) and parses the response into an ExternalFlightApiResponse object.
 */
@Service
public class ExternalFlightService {
    
    /**
     * Builds a url using the provided departure and arrival airports and uses that to get data from https://api.aviationstack.com/v1/flights
     * @param departureAirport
     * @param arrivalAirport
     * @param accessKey Key needed to access the external API
     * @return The response from the api
     * @throws Exception
     * @throws IllegalArgumentException
     */
    public ExternalFlightApiResponse getExternalFlightData(String departureAirport, String arrivalAirport, String accessKey) 
        throws Exception, IllegalArgumentException {

        Gson gson = new Gson(); 

        URL externalDataUrl;
        String jsonString = "";
    
        if (!departureAirport.matches("[A-Z]{3}") || !arrivalAirport.matches("[A-Z]{3}")) {
        
        throw new IllegalArgumentException("Airport codes must be 3- capital letter IATA code.");
        }
        System.out.println("Starting...");
        
        // Build the URL, depending on which query paramaters have been given.
        URIBuilder externalDataUrlBuilder = new URIBuilder("https://api.aviationstack.com/v1/flights");
        if (departureAirport != null) {
            externalDataUrlBuilder.addParameter("dep_iata", departureAirport);
        }
        if (arrivalAirport != null) {
            externalDataUrlBuilder.addParameter("arr_iata", arrivalAirport);
        }
        externalDataUrlBuilder.addParameter("access_key", accessKey);

        // Convert components into an actual URL
        externalDataUrl = externalDataUrlBuilder.build().toURL(); 
        
        // Call the API
        HttpURLConnection httpUrlConnection = (HttpURLConnection) externalDataUrl.openConnection();
        httpUrlConnection.setRequestMethod("GET");
        httpUrlConnection.connect();

        int responsecode = httpUrlConnection.getResponseCode();
        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        }

        // Read response
        Scanner scanner = new Scanner(externalDataUrl.openStream());
        while (scanner.hasNext()) {
            jsonString += scanner.nextLine();
        }
        scanner.close();
        
        // Parse with Gson
        gson = new Gson();
        Type type = new TypeToken<ExternalFlightApiResponse>(){}.getType();
        
        ExternalFlightApiResponse apiResponse = gson.fromJson(jsonString, type);

        for (Data flight : apiResponse.getData()) {
        System.out.println("Departure: " + flight.getDeparture().getIata() +
                           " and Arrival: " + flight.getArrival().getIata() +
                           " and Departure Date: " + flight.getFlightDate());
        }  
        return apiResponse;
    }
}
