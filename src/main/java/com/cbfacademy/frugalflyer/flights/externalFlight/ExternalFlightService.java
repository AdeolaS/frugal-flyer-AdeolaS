package com.cbfacademy.frugalflyer.flights.externalFlight;

import java.net.URL;
import java.util.Scanner;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URI;

@Service
public class ExternalFlightService {

    public void getExternalFlightData(String departureAirport, String arrivalAirport, String accessKey) throws Exception{

        String jsonString = "";

        System.out.println("Starting...");
        // Build the URL, depending on which query paramaters have been given.
        try {
            URIBuilder externalDataUrlBuilder = new URIBuilder("https://api.aviationstack.com/v1/flights");
            if (departureAirport != null) {
                externalDataUrlBuilder.addParameter("dep_iata", departureAirport);
            }
            if (arrivalAirport != null) {
                externalDataUrlBuilder.addParameter("arr_iata", arrivalAirport);
            }
            externalDataUrlBuilder.addParameter("limit", "3");
            externalDataUrlBuilder.addParameter("access_key", accessKey);

            // Convert components into an actual URL
            URL externalDataUrl = externalDataUrlBuilder.build().toURL();

            HttpURLConnection httpUrlConnection = (HttpURLConnection) externalDataUrl.openConnection();
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.connect();

            //Getting the response code
            int responsecode = httpUrlConnection.getResponseCode();

            // If response code isn't OK
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
                Scanner scanner = new Scanner(externalDataUrl.openStream());
            
                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    jsonString += scanner.nextLine();                
                }
                //Close the scanner
                scanner.close();
                System.out.println(jsonString);
            }
            
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }

        if (jsonString.isEmpty()) {
            System.out.println("No data received from API.");
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ExternalFlightApiResponse apiResponse = objectMapper.readValue(jsonString, ExternalFlightApiResponse.class);

        if (apiResponse.getData() == null || apiResponse.getData().isEmpty()) {
            System.out.println("No flights found.");
            return;
        }

        for (Data flight : apiResponse.getData()) {
            System.out.println("Departure: " + flight.getDeparture().getIata()
                    + " → Arrival: " + flight.getArrival().getIata());
        }
        

            
        
    }
    
    
}








// package com.cbfacademy.frugalflyer.flights.externalFlight;

// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// import java.util.Map;

// import org.springframework.stereotype.Service;

// import com.cbfacademy.frugalflyer.flights.utility.HttpRequestBuilder;
// import com.cbfacademy.frugalflyer.flights.utility.HttpResponseParser;

// @Service
// public class ExternalFlightService {
    
//     public void getExternalFlightData() throws Exception {

//         // Build HTTP Request using HttpRequestBuilder
//         String url = "https://api.aviationstack.com/v1/flights?dep_iata=LHR&arr_iata=JFK&access_key=e4c48a8c4963d21a5e20e6c37b3c2043";
//         HttpRequest request = HttpRequestBuilder.build(url);

//         HttpClient client = HttpClient.newBuilder()
//                 .version(HttpClient.Version.HTTP_1_1)
//                 .build();

//         HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//         // Parse the response using HttpResponseParser and display the results
//         Map<String, String> responseData = HttpResponseParser.parse(response);




//         System.out.println("=== HTTP Response Analysis ===");
//             System.out.println("Request URL: " + responseData.get("URL"));
//             System.out.println("Status Code: " + responseData.get("Status"));
//             if (responseData.containsKey("Server")) {
//                 System.out.println("Server: " + responseData.get("Server"));
//             }
//             if (responseData.containsKey("Content-Type")) {
//                 System.out.println("Content-Type: " + responseData.get("Content-Type"));
//             }
//             if (responseData.containsKey("Content-Length")) {
//                 System.out.println("Content-Length: " + responseData.get("Content-Length"));
//             }
//             System.out.println("================================");
            
//         String body = response.body();

//         System.out.println(body);
//     }
    
// }
