package com.profITsoft.block2.exception;

/**
 * FlightScheduleException class to handle cases when a flight schedule is invalid
 */
public class FlightScheduleException extends RuntimeException {

    public FlightScheduleException(String message) {
        super(message);
    }
}
