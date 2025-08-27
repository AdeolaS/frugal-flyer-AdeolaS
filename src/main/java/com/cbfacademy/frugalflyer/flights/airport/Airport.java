package com.cbfacademy.frugalflyer.flights.airport;

import com.cbfacademy.frugalflyer.flights.destination.Destination;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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


    @ManyToOne
    @JoinColumn(name = "destination_id")
    // To prevent infinite recursion caused by bidirectional relationship.
    @JsonIgnore
    private Destination destination;

    /**
	 * Default no-params constructor.
     * Initialises all fields to null.
	 */
    public Airport() {
        this(null, null, null);
    }

 
    public Airport(String code, String name, Destination destination) {
        this.code = code;
        this.name = name;
        this.destination = destination;
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

    public Destination getDestination() {
        return this.destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
