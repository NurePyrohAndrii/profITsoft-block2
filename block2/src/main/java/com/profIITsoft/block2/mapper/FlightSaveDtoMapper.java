package com.profIITsoft.block2.mapper;

import com.profIITsoft.block2.dto.FlightSaveDto;
import com.profIITsoft.block2.entity.Airport;
import com.profIITsoft.block2.entity.Flight;
import com.profIITsoft.block2.entity.Service;

import java.time.Instant;
import java.util.Set;

/**
 * Flight save DTO mapper class to map Flight save DTO to Flight entity
 */
@org.springframework.stereotype.Service
public class FlightSaveDtoMapper {

    public Flight toFlightEntity(FlightSaveDto flightSaveDto, Set<Airport> airports, Set<Service> services) {
        Flight flight = new Flight();
        flight.setFlightNumber(flightSaveDto.getFlightNumber());
        flight.setDepartureAirport(airports.stream().findFirst().get());
        flight.setArrivalAirport(airports.stream().skip(1).findFirst().get());
        flight.setDepartureTime(Instant.parse(flightSaveDto.getDepartureTime()));
        flight.setArrivalTime(Instant.parse(flightSaveDto.getArrivalTime()));
        flight.setServices(services);
        return flight;
    }
}
