package com.profIITsoft.block2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Airport entity class to represent an airport.
 */
@Entity
@Table(name = "airport")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "airport_code", nullable = false)
    private String airportCode;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "timezone")
    private String timezone;

    @OneToMany(mappedBy = "departureAirport")
    private Set<Flight> departures;

    @OneToMany(mappedBy = "arrivalAirport")
    private Set<Flight> arrivals;
}
