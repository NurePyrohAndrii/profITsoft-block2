package com.profIITsoft.block2.mapper;

import com.profIITsoft.block2.dto.FlightDto;
import com.profIITsoft.block2.entity.Airport;
import com.profIITsoft.block2.entity.Flight;
import com.profIITsoft.block2.entity.Service;

import java.time.Instant;
import java.util.Set;

/**
 * Flight save DTO mapper class to map Flight save DTO to Flight entity
 */
@org.springframework.stereotype.Service
public class FlightDtoMapper {

    public Flight toFlight(FlightDto flightDto, Set<Airport> airports, Set<Service> services) {
        Flight flight = new Flight();
        setFlightDetails(flight, flightDto, airports, services);
        return flight;
    }

    public void updateFlight(Flight flight, FlightDto flightDto, Set<Airport> airports, Set<Service> services) {
        setFlightDetails(flight, flightDto, airports, services);
    }

    private void setFlightDetails(Flight flight, FlightDto flightDto, Set<Airport> airports, Set<Service> services) {
        flight.setFlightNumber(flightDto.getFlightNumber());
        flight.setDepartureAirport(airports.stream().findFirst().get());
        flight.setArrivalAirport(airports.stream().skip(1).findFirst().get());
        flight.setDepartureTime(Instant.parse(flightDto.getDepartureTime()));
        flight.setArrivalTime(Instant.parse(flightDto.getArrivalTime()));
        flight.setServices(services);
    }
}
