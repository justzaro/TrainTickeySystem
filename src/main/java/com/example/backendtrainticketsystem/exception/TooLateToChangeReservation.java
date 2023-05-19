package com.example.backendtrainticketsystem.exception;

public class TooLateToChangeReservation extends RuntimeException {
    public TooLateToChangeReservation(String message) {
        super(message);
    }
}
