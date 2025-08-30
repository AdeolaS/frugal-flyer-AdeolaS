package com.cbfacademy.frugalflyer.flights.destination;

import java.util.ArrayList;
import java.util.List;

import com.cbfacademy.frugalflyer.flights.airport.Airport;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * The Destination class the represents an destination entity with details such as ID, city, country, climate, tags and Airports.
 */
@Entity
@Table(name = "destinations")
public class Destination {
    
    /**
     * ID of the Destination which is generated
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * City name of destination
     */
    private String city;
    /**
     * Country name of destination
     */
    private String country;
    /**
     * Climate type of destination. E.g. temperate, tropical, arctic, desert etc.
     */
    private String climate;

    /**
     * List of Strings containing the tags to describe the destination. Will allow destinations to be filtered.
     * ElementCollection Annotation creates another table called destination_tags.
     */
    @ElementCollection
    private List<String> tags = new ArrayList<>();

    /**
     * List of Airports that are in the destination
     */
    @OneToMany(mappedBy = "destination")
    private List<Airport> airports;

    /**
     * Default constructor required by JPA
     */
    public Destination() {}

    /**
     * Parameterised constructor of destination
     * @param city name of city
     * @param country name of country
     * @param climate type of climate
     * @param tags descriptive tags of the destination e.g temperate, tropical
     * @param airports airports present in the destination
     */
    public Destination(String city, String country, String climate, List<String> tags, List<Airport> airports) {
        this.city = city;
        this.country = country;
        this.climate = climate;
        this.tags = tags;
        this.airports = airports;
    }

    /**
     * get ID of Destination
     * @return ID of destination
     */
    public Long getId() {
        return id;
    }

    /**
     * get city of Destination
     * @return name of city of destination
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of Destination
     * @param city name of the Destination's city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * get country of Destination
     * @return name of country of destination
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of Destination
     * @param country name of the Destination's country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the destination's climate
     * @return climate type of destination
     */
    public String getClimate() {
        return climate;
    }

    /**
     * Sets the destination's climate
     * @param climate climate type of destination
     */
    public void setClimate(String climate) {
        this.climate = climate;
    }

    /**
     * gets list of descriptive tags of the destination
     * @return list of tags for the destination
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * sets list of descriptive tags of the destination
     * @param tags list of tags for the destination
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * gets list of airports at the destination
     * @return list of Airports
     */
    public List<Airport> getAirports() {
        return airports;
    }

    /**
     * sets list of airports at the destinatio
     * @param airports list of Airports
     */
    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }
    
}
