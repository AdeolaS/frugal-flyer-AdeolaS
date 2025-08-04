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

@Entity
@Table(name = "destinations")
public class Destination {
    
    @Id
    @GeneratedValue
    private Long id;

    private String city;
    private String country;
    private String climate;

    /**
     * List of Strings containing the tags to describe the destination. Will allow destinations to be filtered.
     * ElementCollection Annotation creates another table called destination_tags.
     */
    @ElementCollection
    private List<String> tags = new ArrayList<>();

    @OneToMany(mappedBy = "destination")
    private List<Airport> airports;

    //public Destination(String city, String country, String climate, )
    public Destination() {
        // Default constructor required by JPA
    }

    public Destination(String city, String country, String climate, List<String> tags, List<Airport> airports) {
        this.city = city;
        this.country = country;
        this.climate = climate;
        this.tags = tags;
        this.airports = airports;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }
    
}
