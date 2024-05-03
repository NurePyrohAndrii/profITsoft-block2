package com.profIITsoft.block2.service;

import com.profIITsoft.block2.dto.FlightDetailsDto;
import com.profIITsoft.block2.dto.FlightDto;
import com.profIITsoft.block2.entity.Airport;
import com.profIITsoft.block2.entity.Flight;
import com.profIITsoft.block2.entity.Service;
import com.profIITsoft.block2.exception.AirportNotFoundException;
import com.profIITsoft.block2.exception.FlightNotFoundException;
import com.profIITsoft.block2.exception.FlightScheduleException;
import com.profIITsoft.block2.exception.ServiceNotFoundException;
import com.profIITsoft.block2.mapper.FlightDetailsDtoMapper;
import com.profIITsoft.block2.mapper.FlightDtoMapper;
import com.profIITsoft.block2.repository.FlightRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Optional;
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
    private final FlightDtoMapper flightDtoMapper;
    private final FlightDetailsDtoMapper flightDetailsDtoMapper;

    /**
     * Save a new flight with the provided details.
     *
     * @param flightDto flight DTO
     * @return flight details DTO
     */
    public FlightDetailsDto saveFlight(FlightDto flightDto) {
        // validate the flight request
        FlightRequestValidationResult result = getFlightRequestValidationResult(flightDto);

        // ensure that the combination of flight number, departure and arrival airports,
        // departure and arrival time is unique, so that we don't have two flights
        // with the same number departing at the same time
        if (flightRepository.existsByFlightDetails(
                flightDto.getFlightNumber(),
                result.airports().stream().findFirst().get(),
                result.airports().stream().skip(1).findFirst().get(),
                result.departureTime(),
                result.arrivalTime()
        )) {
            throw new FlightScheduleException("flight.exists");
        }

        // create the flight, save it in the database and return its details
        return flightDetailsDtoMapper.toFlightDetailsDto(
                flightRepository.save(
                        flightDtoMapper.toFlight(flightDto, result.airports(), result.services())
                )
        );
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

    /**
     * Update the details of a flight with the provided flightId.
     *
     * @param flightId flight ID
     * @param flightDto flight DTO
     * @return flight details DTO
     */
    public FlightDetailsDto updateFlight(Long flightId, FlightDto flightDto) {
        // validate the flight request
        FlightRequestValidationResult result = getFlightRequestValidationResult(flightDto);

        Optional<Flight> flightWithRequestedScheduling = flightRepository.findByFlightDetails(
                flightDto.getFlightNumber(),
                result.airports().stream().findFirst().get(),
                result.airports().stream().skip(1).findFirst().get(),
                result.departureTime(),
                result.arrivalTime()
        );

        // ensure that flight with the requested scheduling does not exist or it is the same flight
        if (flightWithRequestedScheduling.isPresent() && !flightWithRequestedScheduling.get().getId().equals(flightId)) {
            throw new FlightScheduleException("invalid.update.flight");
        }

        // get the flight details
        Flight flight = flightRepository.findById(flightId).orElseThrow(
                () -> new FlightNotFoundException("not.found.flight")
        );

        // update the flight details
        flightDtoMapper.updateFlight(flight, flightDto, result.airports(), result.services());

        // create the flight, save it in the database and return its details
        return flightDetailsDtoMapper.toFlightDetailsDto(
                flightRepository.save(flight)
        );
    }

    /**
     * Delete a flight with the provided flightId.
     *
     * @param flightId flight ID
     */
    public void deleteFlight(Long flightId) {
        // ensure that the flight exists
        if (!flightRepository.existsById(flightId)) {
            throw new FlightNotFoundException("not.found.flight");
        }

        // delete the flight
        flightRepository.deleteById(flightId);
    }

    /**
     * Private method to validate the flight request.
     *
     * @param flightDto flight DTO
     * @return flight request validation result
     */
    private FlightRequestValidationResult getFlightRequestValidationResult(FlightDto flightDto) {
        // ensure that arrival time and departure time are valid
        Instant departureTime = Instant.parse(flightDto.getDepartureTime());
        Instant arrivalTime = Instant.parse(flightDto.getArrivalTime());

        if (arrivalTime.isBefore(departureTime) || departureTime.isBefore(Instant.now())) {
            throw new FlightScheduleException("invalid.arrival.departure.time");
        }

        Set<Airport> airports = airportService.findAirportsByCodes(
                flightDto.getDepartureAirport(),
                flightDto.getArrivalAirport()
        );

        // ensure that requested airports exist
        if (airports.size() < 2) {
            throw new AirportNotFoundException("not.found.airports");
        }

        // ensure that services exist
        Set<Service> services = serviceService.findServicesByNames(flightDto.getServices());

        if (services.size() != flightDto.getServices().size()) {
            throw new ServiceNotFoundException("not.found.services");
        }
        return new FlightRequestValidationResult(departureTime, arrivalTime, airports, services);
    }

    /**
     * Private record to hold the result of the flight request validation
     */
    private record FlightRequestValidationResult(Instant departureTime, Instant arrivalTime, Set<Airport> airports,
                                                 Set<Service> services) {}
}
