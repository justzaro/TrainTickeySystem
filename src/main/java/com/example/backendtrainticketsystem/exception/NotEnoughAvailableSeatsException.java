package com.example.backendtrainticketsystem.exception;

public class NotEnoughAvailableSeatsException extends RuntimeException {
    public NotEnoughAvailableSeatsException(String message) {
        super(message);
    }
}
