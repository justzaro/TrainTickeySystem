package com.example.backendtrainticketsystem.exception;

public class NoTrainsAvailableForGivenPlaceOfArrivalAndTimeOfDeparture extends RuntimeException {
    public NoTrainsAvailableForGivenPlaceOfArrivalAndTimeOfDeparture(String message) {
        super(message);
    }
}
