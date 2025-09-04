package com.cbfacademy.frugalflyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cbfacademy.frugalflyer.flights.externalFlight.ExternalFlightService;

@SpringBootApplication
public class FrugalflyerApplication {

	public static void main(String[] args) {

		ExternalFlightService externalFlightService = new ExternalFlightService();
		try {
			externalFlightService.getExternalFlightData("LHR", "JFK", "e4c48a8c4963d21a5e20e6c37b3c2043");
		} catch(Exception e) {

		}
		//SpringApplication.run(FrugalflyerApplication.class, args);

		
		
	}

}
