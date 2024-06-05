package com.profITsoft.block2.exception;

/**
 * FlightNotFoundException class to handle cases where a flight is not found
 */
public class FlightNotFoundException extends ResourceNotFoundException {

    public FlightNotFoundException(String message) {
        super(message);
    }
}
