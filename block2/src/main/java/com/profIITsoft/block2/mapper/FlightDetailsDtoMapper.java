package com.profIITsoft.block2.mapper;

import com.profIITsoft.block2.dto.FlightDetailsDto;
import com.profIITsoft.block2.entity.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Flight details DTO mapper class to map Flight entity to Flight details DTO
 */
@Service
@RequiredArgsConstructor
public class FlightDetailsDtoMapper {

    private final AirportDtoMapper airportDtoMapper;

    public FlightDetailsDto toFlightDetailsDto(Flight flight) {
        return FlightDetailsDto.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .departureAirport(airportDtoMapper.apply(flight.getDepartureAirport()))
                .arrivalAirport(airportDtoMapper.apply(flight.getArrivalAirport()))
                .departureTime(flight.getDepartureTime().toString())
                .arrivalTime(flight.getArrivalTime().toString())
                .services(flight.getServices())
                .build();
    }
}
