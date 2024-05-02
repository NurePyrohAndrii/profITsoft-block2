package com.profIITsoft.block2.service;

import com.profIITsoft.block2.dto.FlightDetailsDto;
import com.profIITsoft.block2.dto.FlightSaveDto;
import com.profIITsoft.block2.entity.Airport;
import com.profIITsoft.block2.entity.Service;
import com.profIITsoft.block2.exception.AirportNotFoundException;
import com.profIITsoft.block2.exception.FlightNotFoundException;
import com.profIITsoft.block2.exception.FlightScheduleException;
import com.profIITsoft.block2.exception.ServiceNotFoundException;
import com.profIITsoft.block2.mapper.FlightDetailsDtoMapper;
import com.profIITsoft.block2.mapper.FlightSaveDtoMapper;
import com.profIITsoft.block2.repository.FlightRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Set;

/**
 * Flight service class to manage flights
 */
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportService airportService;
    private final ServiceService serviceService;
    private final FlightSaveDtoMapper flightSaveDtoMapper;
    private final FlightDetailsDtoMapper flightDetailsDtoMapper;

    /**
     * Save a flight in the database with the provided details in flightSaveDto.
     *
     * @param flightSaveDto flight save DTO
     * @return flight details DTO
     */
    // TODO: Slice this method into smaller methods
    public Long saveFlight(FlightSaveDto flightSaveDto) {
        // ensure that arrival time and departure time are valid
        Instant departureTime = Instant.parse(flightSaveDto.getDepartureTime());
        Instant arrivalTime = Instant.parse(flightSaveDto.getArrivalTime());

        if (arrivalTime.isBefore(departureTime) || departureTime.isBefore(Instant.now())) {
            throw new FlightScheduleException("invalid.arrival.departure.time");
        }

        Set<Airport> airports = airportService.findAirportsByCodes(
                flightSaveDto.getDepartureAirport(),
                flightSaveDto.getArrivalAirport()
        );

        // ensure that requested airports exist
        if (airports.size() < 2) {
            throw new AirportNotFoundException("not.found.airports");
        }

        // ensure that services exist
        Set<Service> services = serviceService.findServicesByNames(flightSaveDto.getServices());

        if (services.size() != flightSaveDto.getServices().size()) {
            throw new ServiceNotFoundException("not.found.services");
        }

        // ensure that the combination of flight number, departure and arrival airports,
        // departure and arrival time is unique, so that we don't have two flights
        // with the same number departing at the same time
        if (flightRepository.existsByFlightDetails(
                flightSaveDto.getFlightNumber(),
                airports.stream().findFirst().get(),
                airports.stream().skip(1).findFirst().get(),
                departureTime,
                arrivalTime
        )) {
            throw new FlightScheduleException("flight.exists");
        }

        // create the flight, save it in the database and return its details
        return flightRepository.save(
                flightSaveDtoMapper.toFlightEntity(flightSaveDto, airports, services)
        ).getId();
    }

    /**
     * Get the details of a flight with the provided flightId.
     *
     * @param flightId flight ID
     * @return flight details DTO
     */
    public FlightDetailsDto getFlightDetails(Long flightId) {
        return flightDetailsDtoMapper.toFlightDetailsDto(
                flightRepository.findById(flightId).orElseThrow(
                        () -> new FlightNotFoundException("not.found.flight")
                )
        );
    }
}
