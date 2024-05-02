package com.profIITsoft.block2.controller;

import com.profIITsoft.block2.common.response.ApiResponse;
import com.profIITsoft.block2.dto.FlightDetailsDto;
import com.profIITsoft.block2.dto.FlightSaveDto;
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
     * @param flightSaveDto flight save DTO
     * @return flight details DTO
     */
    @PostMapping
    public ResponseEntity<ApiResponse<FlightDetailsDto>> saveFlight(
            @RequestBody @Valid FlightSaveDto flightSaveDto
    ) {
        Long createdFlightId = flightService.saveFlight(flightSaveDto);
        return ResponseEntity.created(URI.create("api/flights/" + createdFlightId))
                .body(ApiResponse.success(null, HttpStatus.CREATED.value()));
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

}
