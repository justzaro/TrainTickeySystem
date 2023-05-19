package com.example.backendtrainticketsystem.service;

import com.example.backendtrainticketsystem.common.enums.Role;
import com.example.backendtrainticketsystem.common.enums.TicketType;
import com.example.backendtrainticketsystem.dto.ReservationDto;
import com.example.backendtrainticketsystem.dto.ReservationDtoResponse;
import com.example.backendtrainticketsystem.dto.UserDto;
import com.example.backendtrainticketsystem.dto.UserDtoResponse;
import com.example.backendtrainticketsystem.exception.*;
import com.example.backendtrainticketsystem.model.Reservation;
import com.example.backendtrainticketsystem.model.Route;
import com.example.backendtrainticketsystem.model.User;
import com.example.backendtrainticketsystem.repository.ReservationRepository;
import com.example.backendtrainticketsystem.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.example.backendtrainticketsystem.common.ExceptionMessages.*;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminService(UserRepository userRepository, ReservationRepository reservationRepository,
                        ReservationService reservationService) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.modelMapper = new ModelMapper();
    }

    public UserDtoResponse getUser(String adminUsername, String username) {

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(USER_DOES_NOT_EXIST));

        User admin = userRepository.findUserByUsername(adminUsername)
                .orElseThrow(() -> new UserDoesNotExistException(USER_DOES_NOT_EXIST));

        if (admin.getRole() != Role.ADMIN) {
            throw new NotAnAdminException(NOT_AN_ADMIN);
        }

        return modelMapper.map(user, UserDtoResponse.class);
    }

    public UserDtoResponse updateUser(String adminUsername, String username, UserDto userDto) {

        User userToUpdate = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(USER_DOES_NOT_EXIST));

        User admin = userRepository.findUserByUsername(adminUsername)
                .orElseThrow(() -> new UserDoesNotExistException(USER_DOES_NOT_EXIST));

        if (admin.getRole() != Role.ADMIN) {
            throw new NotAnAdminException(NOT_AN_ADMIN);
        }

        if (userRepository.findUserByUsername(userDto.getUsername()).isPresent()
        && userRepository.findUserByUsername(userDto.getUsername()).get().getUserId() != userToUpdate.getUserId()) {
            throw new UserWithGivenUsernameAlreadyExistsException(USER_WITH_GIVEN_USERNAME_EXISTS);
        }

        userToUpdate = modelMapper.map(userDto, User.class);
        return modelMapper.map(userRepository.save(userToUpdate), UserDtoResponse.class);
    }

    public UserDtoResponse registerUser(String adminUsername, UserDto userDto) {

        User admin = userRepository.findUserByUsername(adminUsername)
                .orElseThrow(() -> new UserDoesNotExistException(USER_DOES_NOT_EXIST));

        if (admin.getRole() != Role.ADMIN) {
            throw new NotAnAdminException(NOT_AN_ADMIN);
        }

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


    public ReservationDtoResponse changeReservationPrice(String adminUsername, int reservationId) {

        User admin = userRepository.findUserByUsername(adminUsername)
                .orElseThrow(() -> new UserDoesNotExistException(USER_DOES_NOT_EXIST));

        if (admin.getRole() != Role.ADMIN) {
            throw new NotAnAdminException(NOT_AN_ADMIN);
        }

        Reservation reservation =
                reservationRepository.findById(reservationId)
                        .orElseThrow(() -> new InvalidReservationIdException(INVALID_RESERVATION_ID));


        Route route = reservation.getRoute();
        User user = reservation.getUser();

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setTicketCount(reservation.getTicketCount());
        reservationDto.setTicketType(reservation.getTicketType());
        reservationDto.setChildAge(0);
        reservationDto.setChildUnderSixteen(reservation.isChildIsUnderSixteen());
        reservationDto.setDateOfDeparture(reservation.getDateOfDeparture());

        double reservationPrice =
                reservationService.getTicketPrice(route, user, reservationDto);
        reservation.setPrice(reservationPrice);

        return modelMapper.map(reservationRepository.save(reservation), ReservationDtoResponse.class);
    }

}
