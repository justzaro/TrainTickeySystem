package com.example.backendtrainticketsystem.service;

import com.example.backendtrainticketsystem.common.enums.Role;
import com.example.backendtrainticketsystem.dto.UserDto;
import com.example.backendtrainticketsystem.dto.UserDtoResponse;
import com.example.backendtrainticketsystem.exception.NotAnAdminException;
import com.example.backendtrainticketsystem.exception.UserDoesNotExistException;
import com.example.backendtrainticketsystem.exception.UserWithGivenEmailAlreadyExistsException;
import com.example.backendtrainticketsystem.exception.UserWithGivenUsernameAlreadyExistsException;
import com.example.backendtrainticketsystem.model.User;
import com.example.backendtrainticketsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.backendtrainticketsystem.common.ExceptionMessages.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    public UserDtoResponse getUser(String username) {

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(USER_DOES_NOT_EXIST));

        return modelMapper.map(user, UserDtoResponse.class);
    }

    public UserDtoResponse updateUser(String adminUsername, String username, UserDto userDto) {

        User userToUpdate = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(USER_DOES_NOT_EXIST));

        if (userRepository.findUserByUsername(adminUsername).get().getRole() != Role.ADMIN) {
            throw new NotAnAdminException(NOT_AN_ADMIN);
        }

        userToUpdate = modelMapper.map(userDto, User.class);

        return modelMapper.map(userRepository.save(userToUpdate), UserDtoResponse.class);
    }

    public UserDtoResponse registerUser(UserDto userDto) {

        if (userRepository.findUserByEmail(userDto.getEmail()).isPresent()) {
            throw new UserWithGivenEmailAlreadyExistsException(USER_WITH_GIVEN_EMAIL_EXISTS);
        }

        if (userRepository.findUserByUsername(userDto.getUsername()).isPresent()) {
            throw new UserWithGivenUsernameAlreadyExistsException(USER_WITH_GIVEN_USERNAME_EXISTS);
        }

        User userToRegister = modelMapper.map(userDto, User.class);
        userRepository.save(userToRegister);

        return modelMapper.map(userToRegister, UserDtoResponse.class);
    }

}
