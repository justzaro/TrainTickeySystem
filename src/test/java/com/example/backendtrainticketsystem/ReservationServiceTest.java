package com.example.backendtrainticketsystem;

import com.example.backendtrainticketsystem.common.enums.TicketType;
import com.example.backendtrainticketsystem.dto.ReservationDto;

import com.example.backendtrainticketsystem.exception.InvalidDateOfDeparture;
import com.example.backendtrainticketsystem.exception.NotEnoughAvailableSeatsException;
import com.example.backendtrainticketsystem.exception.RouteDoesNotExistException;
import com.example.backendtrainticketsystem.exception.UserDoesNotExistException;
import com.example.backendtrainticketsystem.model.User;
import com.example.backendtrainticketsystem.repository.ReservationRepository;
import com.example.backendtrainticketsystem.repository.UserRepository;
import com.example.backendtrainticketsystem.service.ReservationService;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReservationRepository reservationRepository;

    //Test Case 5 - calculateTicketPriceBasedOnCardType()
    @Test
    public void ticket_price_should_equal_hundred() {
        User user = userRepository.findUserByUsername("zaro").get();

        ReservationDto reservation = new ReservationDto();
        reservation.setTicketCount(1);
        reservation.setTicketType(TicketType.ONE_WAY);
        reservation.setChildUnderSixteen(true);
        reservation.setDateOfDeparture(LocalDate.of(2023, 4, 20));

        assertEquals(
                reservationService.calculateTicketPriceBasedOnCardType(100, user, reservation), 100, 2);
    }

    //Test Case 6 - calculateTicketPriceBasedOnCardType()
    @Test
    public void ticket_price_should_equal_three_hundred() {
        User user = userRepository.findUserByUsername("zaro").get();

        ReservationDto reservation = new ReservationDto();
        reservation.setTicketCount(3);
        reservation.setTicketType(TicketType.ONE_WAY);
        reservation.setChildUnderSixteen(false);
        reservation.setDateOfDeparture(LocalDate.of(2023, 4, 20));

        assertEquals(
                reservationService.calculateTicketPriceBasedOnCardType(100, user, reservation), 300, 2);
    }

    //Test Case 4 - calculateTicketPriceBasedOnCardType()
    @Test
    public void ticket_price_should_equal_sixty_two_and_seventy() {
        User user = userRepository.findUserByUsername("petar").get();

        ReservationDto reservation = new ReservationDto();
        reservation.setTicketCount(1);
        reservation.setTicketType(TicketType.ONE_WAY);
        reservation.setChildUnderSixteen(false);
        reservation.setDateOfDeparture(LocalDate.of(2023, 4, 20));

        assertEquals(
                reservationService.calculateTicketPriceBasedOnCardType(95, user, reservation), 62.70, 2);
    }

    //Test Case 3 - calculateTicketPriceBasedOnCardType()
    @Test
    public void ticket_price_should_equal_hundred_fifty_seven_and_seventy() {
        User user = userRepository.findUserByUsername("petar").get();

        ReservationDto reservation = new ReservationDto();
        reservation.setTicketCount(2);
        reservation.setTicketType(TicketType.ONE_WAY);
        reservation.setChildUnderSixteen(true);
        reservation.setDateOfDeparture(LocalDate.of(2023, 4, 20));

        assertEquals(
                reservationService.calculateTicketPriceBasedOnCardType(95, user, reservation), 157.70, 2);
    }

    //Test Case 1 - calculateTicketPriceBasedOnCardType()
    @Test
    public void ticket_price_should_equal_fifty() {
        User user = userRepository.findUserByUsername("ivana").get();

        ReservationDto reservation = new ReservationDto();
        reservation.setTicketCount(1);
        reservation.setTicketType(TicketType.ONE_WAY);
        reservation.setChildUnderSixteen(true);
        reservation.setDateOfDeparture(LocalDate.of(2023, 4, 20));

        assertEquals(
                reservationService.calculateTicketPriceBasedOnCardType(100, user, reservation), 50, 2);
    }

    //Test Case 2 - calculateTicketPriceBasedOnCardType()
    @Test
    public void ticket_price_should_equal_ninety() {
        User user = userRepository.findUserByUsername("ivana").get();

        ReservationDto reservation = new ReservationDto();
        reservation.setTicketCount(1);
        reservation.setTicketType(TicketType.ONE_WAY);
        reservation.setChildUnderSixteen(false);
        reservation.setDateOfDeparture(LocalDate.of(2023, 4, 20));

        assertEquals(
                reservationService.calculateTicketPriceBasedOnCardType(100, user, reservation), 90, 2);
    }

    //Test Case 1 - calculateTicketPriceBasedOnTicketType()
    @Test
    public void ticket_price_should_equal_two_hundred() {
        assertEquals(
                reservationService.calculateTicketPriceBasedOnTicketType(TicketType.ONE_WAY, 200), 200, 2);
    }

    //Test Case 2 - calculateTicketPriceBasedOnTicketType()
    @Test
    public void ticket_price_should_equal_four_hundred() {
        assertEquals(
                reservationService.calculateTicketPriceBasedOnTicketType(TicketType.TWO_WAY, 200), 400, 2);
    }

    //Test cases 5, 6, 7, 8 - createReservation()
    @Test(expected = RouteDoesNotExistException.class)
    public void should_throw_route_does_not_exist_exception() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setTicketCount(1);
        reservationDto.setTicketType(TicketType.ONE_WAY);
        reservationDto.setChildUnderSixteen(false);
        reservationDto.setDateOfDeparture(LocalDate.of(2023, 5, 30));

        reservationService.createReservation(1, "zaro", reservationDto);
    }

    //Test cases 9, 10, 11, 12 - createReservation()
    @Test(expected = UserDoesNotExistException.class)
    public void should_throw_user_does_not_exist_exception() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setTicketCount(1);
        reservationDto.setTicketType(TicketType.ONE_WAY);
        reservationDto.setChildUnderSixteen(false);
        reservationDto.setDateOfDeparture(LocalDate.of(2023, 5, 30));

        reservationService.createReservation(17, "zarozaro", reservationDto);
    }

    //Test cases 1, 2 - createReservation()
    @Test(expected = InvalidDateOfDeparture.class)
    public void should_throw_invalid_date_of_departure_exception() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setTicketCount(1);
        reservationDto.setTicketType(TicketType.ONE_WAY);
        reservationDto.setChildUnderSixteen(false);
        reservationDto.setDateOfDeparture(LocalDate.of(2023, 4, 10));

        reservationService.createReservation(17, "zaro", reservationDto);
    }

    //Test cases 3 - createReservation()
    @Test(expected = NotEnoughAvailableSeatsException.class)
    public void should_throw_not_enough_available_seats_exception() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setTicketCount(1);
        reservationDto.setTicketType(TicketType.ONE_WAY);
        reservationDto.setChildUnderSixteen(false);
        reservationDto.setDateOfDeparture(LocalDate.of(2023, 5, 30));

        reservationService.createReservation(32, "zaro", reservationDto);
    }

    //Test case 4 - createReservation()
    @Test
    public void should_create_reservation() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setTicketCount(1);
        reservationDto.setTicketType(TicketType.ONE_WAY);
        reservationDto.setChildUnderSixteen(false);
        reservationDto.setDateOfDeparture(LocalDate.of(2023, 5, 25));

        User user = userRepository.findUserByUsername("zaro").get();

        int reservationCountBeforeCreatingReservation = reservationRepository.countReservationByUser(user);
        reservationService.createReservation(17, "zaro", reservationDto);
        int reservationCountAfterCreatingReservation = reservationRepository.countReservationByUser(user);

        assertEquals(reservationCountAfterCreatingReservation, reservationCountBeforeCreatingReservation + 1);
    }
}
