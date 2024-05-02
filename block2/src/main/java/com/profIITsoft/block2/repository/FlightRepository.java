package com.profIITsoft.block2.repository;

import com.profIITsoft.block2.entity.Airport;
import com.profIITsoft.block2.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

/**
 * Flight repository interface to interact with the database
 */
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
            SELECT COUNT(f) > 0
                FROM Flight f
                WHERE f.flightNumber = :flightNumber
                AND f.departureAirport = :departureAirport
                AND f.arrivalAirport = :arrivalAirport
                AND f.departureTime = :departureTime
                AND f.arrivalTime = :arrivalTime
            """)
    boolean existsByFlightDetails(String flightNumber, Airport departureAirport, Airport arrivalAirport, Instant departureTime, Instant arrivalTime);

}
