package com.profITsoft.block2.exception;

/**
 * Exception to be thrown when an airport with the same code already exists.
 */
public class AirportDuplicateException extends ResourceDuplicateException {

    public AirportDuplicateException(String message) {
        super(message);
    }
}
