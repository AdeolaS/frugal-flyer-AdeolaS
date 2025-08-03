package com.cbfacademy.frugalflyer.flights.airport;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The Airport class that represents an airport entity with details such as ID, name, city and country.
 */
@Entity
@Table(name = "airports")
public class Airport {

    /**
     * ID of the airport which is its unique IATA code.
     */
    @Id
    private String code;
    /**
	 * The name of the airport.
	 */
    private String name;
    /**
	 * The city that the airport resides in.
	 */
    private String city;
    /**
	 * The country that the airport resides in.
	 */
    private String country;

    /**
	 * Default no-params constructor.
     * Initialises all fields to null.
	 */
    public Airport() {
        this(null, null, null, null);
    }

    /**
     * Parameterised constructor to create an airport with specified details.
     * @param code ID of the airport.
     * @param name The name of the airport.
     * @param city The city that the airport resides in.
     * @param country The country that the airport resides in.
     */
    public Airport(String code, String name, String city, String country) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    /**
     * Set the code of the airport.
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets the name of the airport.
     * 
     * @param name The airport name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the city where the airport is located.
     * 
     * @param city The city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Sets the country where the airport is located.
     * 
     * @param country The country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the code of the airport.
     * 
     * @return The airport code.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Gets the name of the airport.
     * 
     * @return The airport name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the city where the airport is located.
     * 
     * @return The city of the airport.
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Gets the country where the airport is located.
     * 
     * @return The country of the airport.
     */
    public String getCountry() {
        return this.country;
    }
}
