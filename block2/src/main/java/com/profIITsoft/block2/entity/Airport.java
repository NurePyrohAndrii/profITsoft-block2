package com.profIITsoft.block2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Airport entity class to represent an airport.
 */
@Entity
@Table(name = "airport")
@Getter
@Setter
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Airport code. Unique value.
     */
    @Column(name = "airport_code")
    private String airportCode;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "timezone")
    private String timezone;
}
