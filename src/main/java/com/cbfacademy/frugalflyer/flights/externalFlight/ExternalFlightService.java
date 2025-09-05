package com.cbfacademy.frugalflyer.flights.externalFlight;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.Scanner;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.HttpURLConnection;

/**
 * This method fetches flights from an external API (Aviation Stack) and parses the response into an ExternalFlightApiResponse object.
 */
@Service
public class ExternalFlightService {

    ObjectMapper objectMapper = new ObjectMapper();
    Gson gson = new Gson(); 

    URL externalDataUrl;
    String jsonString = "";
    
    public ExternalFlightApiResponse getExternalFlightData(String departureAirport, String arrivalAirport, String accessKey) throws Exception{

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
        Gson gson = new Gson();
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








