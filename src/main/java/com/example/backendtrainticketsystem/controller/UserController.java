package com.example.backendtrainticketsystem.controller;

import com.example.backendtrainticketsystem.dto.UserDto;
import com.example.backendtrainticketsystem.dto.UserDtoResponse;
import com.example.backendtrainticketsystem.model.User;
import com.example.backendtrainticketsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/train-portal/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{username}/get-user")
    public UserDtoResponse getUser(@PathVariable("username") String username) {
        return userService.getUser(username);
    }

    @PostMapping(path = "/register-user")
    public UserDtoResponse registerUser(@Valid @RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }
}
