package com.example.backendtrainticketsystem.exception;

public class RouteDoesNotExistException extends RuntimeException {
    public RouteDoesNotExistException(String message) {
        super(message);
    }
}
