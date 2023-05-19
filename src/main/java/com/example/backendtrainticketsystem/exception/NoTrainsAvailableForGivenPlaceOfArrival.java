package com.example.backendtrainticketsystem.exception;

public class NoTrainsAvailableForGivenPlaceOfArrival extends RuntimeException {
    public NoTrainsAvailableForGivenPlaceOfArrival(String message) {
        super(message);
    }
}
