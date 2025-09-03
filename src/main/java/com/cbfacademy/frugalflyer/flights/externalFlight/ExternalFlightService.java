package com.cbfacademy.frugalflyer.flights.externalFlight;

import java.net.URL;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URI;

@Service
public class ExternalFlightService {

    public void getExternalFlightData() throws Exception{

        try {
            //set the URL object and type cast it into an HttpURLConnection object so that we can set request types and get response codes back.
            URL externalDataUrl = new URI("https://api.aviationstack.com/v1/flights?dep_iata=LHR&arr_iata=JFK&access_key=e4c48a8c4963d21a5e20e6c37b3c2043").toURL();

            HttpURLConnection httpUrlConnection = (HttpURLConnection) externalDataUrl.openConnection();
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.connect();

            //Getting the response code
            int responsecode = httpUrlConnection.getResponseCode();


            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(externalDataUrl.openStream());
            
                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();

                //Close the scanner
                scanner.close();

                System.out.println(inline);
            }
        }




        } catch(Exception e) {
            e.printStackTrace();
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
