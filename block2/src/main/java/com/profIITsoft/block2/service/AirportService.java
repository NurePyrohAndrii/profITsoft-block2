package com.profIITsoft.block2.service;

import com.profIITsoft.block2.entity.Airport;
import com.profIITsoft.block2.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Airport service class to manage airports
 */
@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;

    /**
     * Find airports by their codes (IATA codes)
     *
     * @param departureCode departure airport code
     * @param arrivalCode   arrival airport code
     * @return set of airports
     */
    public Set<Airport> findAirportsByCodes(String departureCode, String arrivalCode) {
        return airportRepository.findByAirportCodeIn(Set.of(departureCode, arrivalCode));
    }

}
