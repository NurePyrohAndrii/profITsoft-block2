package com.profIITsoft.block2.repository;

import com.profIITsoft.block2.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Airport repository class to manage airport data in a database
 */
public interface AirportRepository extends JpaRepository<Airport, String> {

    /**
     * Find airports by airport codes
     *
     * @param codes a set of airport codes to search for
     * @return set of airports
     */
    Set<Airport> findByAirportCodeIn(Set<String> codes);
}
