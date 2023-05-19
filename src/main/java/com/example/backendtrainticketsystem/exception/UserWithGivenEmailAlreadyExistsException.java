package com.example.backendtrainticketsystem.exception;

public class UserWithGivenEmailAlreadyExistsException extends RuntimeException {
    public UserWithGivenEmailAlreadyExistsException(String message) {
        super(message);
    }
}
