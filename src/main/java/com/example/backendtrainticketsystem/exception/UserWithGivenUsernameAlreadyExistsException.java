package com.example.backendtrainticketsystem.exception;

public class UserWithGivenUsernameAlreadyExistsException extends RuntimeException {
    public UserWithGivenUsernameAlreadyExistsException(String message) {
        super(message);
    }
}
