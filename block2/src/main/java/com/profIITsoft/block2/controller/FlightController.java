package com.profIITsoft.block2.controller;

import com.profIITsoft.block2.common.response.ApiResponse;
import com.profIITsoft.block2.dto.FlightDetailsDto;
import com.profIITsoft.block2.dto.FlightDto;
import com.profIITsoft.block2.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * Flight controller class to manage flights API
 */
@RestController
@RequestMapping("api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    /**
     * Save a flight with the provided details in flightSaveDto.
     *
     * @param flightDto flight save DTO
     * @return flight details DTO
     */
    @PostMapping
    public ResponseEntity<ApiResponse<FlightDetailsDto>> saveFlight(
            @RequestBody @Valid FlightDto flightDto
    ) {
        FlightDetailsDto createdFlight = flightService.saveFlight(flightDto);
        return ResponseEntity.created(URI.create("api/flights/" + createdFlight.getId()))
                .body(ApiResponse.success(createdFlight, HttpStatus.CREATED.value()));
    }

    /**
     * Get flight details by flightId.
     *
     * @param flightId flight ID
     * @return flight details DTO
     */
    @GetMapping("/{flightId}")
    public ResponseEntity<ApiResponse<FlightDetailsDto>> getFlightDetails(
            @PathVariable Long flightId
    ) {
        return ResponseEntity.ok(ApiResponse.success(flightService.getFlightDetails(flightId), HttpStatus.OK.value()));
    }

    /**
     * Update a flight with the provided details in flightSaveDto.
     *
     * @param flightDto flight save DTO
     * @return flight details DTO
     */
    @PutMapping("/{flightId}")
    public ResponseEntity<ApiResponse<FlightDetailsDto>> updateFlight(
            @PathVariable Long flightId,
            @RequestBody @Valid FlightDto flightDto
    ) {
        FlightDetailsDto updatedFlight = flightService.updateFlight(flightId, flightDto);
        return ResponseEntity.ok(ApiResponse.success(updatedFlight, HttpStatus.OK.value()));
    }

    /**
     * Delete a flight by flightId.
     *
     * @param flightId flight ID
     * @return flight details DTO
     */
    @DeleteMapping("/{flightId}")
    public ResponseEntity<ApiResponse<Void>> deleteFlight(
            @PathVariable Long flightId
    ) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }

}
