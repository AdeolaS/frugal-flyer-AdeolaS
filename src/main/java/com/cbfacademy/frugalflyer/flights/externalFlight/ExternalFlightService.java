package com.cbfacademy.frugalflyer.flights.externalFlightData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ExternalFlightService {
    
    /**
     * Builds an HttpRequest object with predefined properties.
     * Method: GET, HTTP Version: HTTP_1_1, User-Agent: Mozilla/5.0 (Java Exercise Client),
     * Accept: text/html,application/json,star/star;q=0.8, Timeout: 30 seconds
     * 
     * @param url The URL for the HTTP request
     * @return HttpRequest object configured with the specified properties
     * @throws RuntimeException if the request cannot be built
     */
    public static HttpRequest build(String url) {
        try {
            return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .version(HttpClient.Version.HTTP_1_1)
                .header("User-Agent", "Mozilla/5.0 (Java Exercise Client)")
                .header("Accept", "text/html,application/json,*/*;q=0.8")
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to build HTTP request for URL: " + url, e);
        }
    }

    /**
     * Parses the HttpResponse object and returns a map containing key information:
     * - "URL": Response URL
     * - "Status": HTTP status code as a string
     * - "Server": Server header value (if present)
     * - "Content-Type": Content-Type header value (if present)
     * - "Content-Length": Content-Length header value (if present)
     * 
     * @param response The HttpResponse object to parse
     * @return Map containing the extracted response information
     */
    public static Map<String, String> parse(HttpResponse<String> response) {
        Map<String, String> responseData = new HashMap<>();
        
        responseData.put("URL", response.request().uri().toString());
        responseData.put("Status", String.valueOf(response.statusCode()));
        addHeader(response, "Server", responseData);
        addHeader(response, "Content-Type", responseData);
        addHeader(response, "Content-Length", responseData);
        
        return responseData;
    }
    
    /**
     * Helper method to add a response header value to the response data map, if present
     * 
     * @param response The HttpResponse object
     * @param headerName The name of the header to extract
     * @param responseData The map to add the header value to
     */
    private static void addHeader(HttpResponse<String> response, String headerName, Map<String, String> responseData) {
        Optional<String> headerValue = response.headers().firstValue(headerName);
        if (headerValue.isPresent()) {
            responseData.put(headerName, headerValue.get());
        }
    }
}
