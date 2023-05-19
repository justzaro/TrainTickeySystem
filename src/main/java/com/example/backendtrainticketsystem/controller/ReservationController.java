package com.example.backendtrainticketsystem.controller;

import com.example.backendtrainticketsystem.dto.ReservationDto;
import com.example.backendtrainticketsystem.dto.ReservationDtoResponse;
import com.example.backendtrainticketsystem.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(path = "/train-portal/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(path = "/{username}/get-all-reservations")
    public List<ReservationDtoResponse> getAllReservationsForUser(
            @PathVariable("username") String username) {
        return reservationService.getAllReservationsForUser(username);
    }

    @PostMapping(path = "/{routeId}/{username}/create-reservation")
    public ReservationDtoResponse createReservation(@PathVariable("routeId") int routeId,
                                  @PathVariable("username") String username,
                                  @Valid @RequestBody ReservationDto reservationDto) {
        return reservationService.createReservation(routeId, username, reservationDto);
    }

    @PutMapping(path = "/{reservationId}/change-reservation")
    public ReservationDtoResponse changeReservation(
            @PathVariable("reservationId") int reservationId,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate newDateOfDeparture,
            @RequestParam int routeId) {
        return reservationService.changeReservation(reservationId, routeId, newDateOfDeparture,
                LocalTime.now());
    }

    @DeleteMapping(path = "/{username}/{reservationId}/delete-reservation")
    public List<ReservationDtoResponse> removeReservation(
            @PathVariable("username") String username,
            @PathVariable("reservationId") int id) {
        return reservationService.removeReservation(username, id);
    }
}
