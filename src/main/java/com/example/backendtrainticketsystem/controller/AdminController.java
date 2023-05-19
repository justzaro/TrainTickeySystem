package com.example.backendtrainticketsystem.controller;

import com.example.backendtrainticketsystem.dto.ReservationDtoResponse;
import com.example.backendtrainticketsystem.dto.UserDto;
import com.example.backendtrainticketsystem.dto.UserDtoResponse;
import com.example.backendtrainticketsystem.service.AdminService;
import com.example.backendtrainticketsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/train-portal/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(path = "/{adminUsername}/{username}/get-user")
    public UserDtoResponse getUser(@PathVariable("username") String username,
                                   @PathVariable("adminUsername") String adminUsername) {
        return adminService.getUser(adminUsername, username);
    }

    @PutMapping(path = "/{adminUsername}/{username}/update-user")
    public UserDtoResponse getUser(@PathVariable("adminUsername") String adminUsername,
                                   @PathVariable("username") String username,
                                   @Valid @RequestBody UserDto userDto) {
        return adminService.updateUser(adminUsername, username, userDto);
    }

    @PutMapping(path = "/{adminUsername}/{reservationId}/update-reservation")
    public ReservationDtoResponse changeReservationPrice(@PathVariable("adminUsername") String adminUsername,
                                          @PathVariable("reservationId") int reservationId){
        return adminService.changeReservationPrice(adminUsername, reservationId);
    }

    @PostMapping(path = "/{adminUsername}/register-user")
    public UserDtoResponse updateUser(@Valid @RequestBody UserDto userDto,
                                      @PathVariable("adminUsername") String adminUsername) {
        return adminService.registerUser(adminUsername, userDto);
    }

}
