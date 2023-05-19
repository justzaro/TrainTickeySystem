package com.example.backendtrainticketsystem.exception;

public class InvalidReservationIdException extends RuntimeException {
    public InvalidReservationIdException(String message) {
        super(message);
    }
}
