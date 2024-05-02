package com.profIITsoft.block2.mapper;

import com.profIITsoft.block2.dto.AirportDto;
import com.profIITsoft.block2.entity.Airport;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Airport DTO mapper class to map Airport entity to Airport DTO
 */
@Service
public class AirportDtoMapper implements Function<Airport, AirportDto> {

    @Override
    public AirportDto apply(Airport airport) {
        return AirportDto.builder()
                .airportCode(airport.getAirportCode())
                .name(airport.getName())
                .city(airport.getCity())
                .country(airport.getCountry())
                .timezone(airport.getTimezone())
                .build();
    }
}
