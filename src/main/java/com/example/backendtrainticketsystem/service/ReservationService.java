package com.example.backendtrainticketsystem.service;

import com.example.backendtrainticketsystem.common.enums.DiscountCardType;
import com.example.backendtrainticketsystem.common.enums.TicketType;
import com.example.backendtrainticketsystem.dto.ReservationDto;
import com.example.backendtrainticketsystem.dto.ReservationDtoResponse;
import com.example.backendtrainticketsystem.exception.*;
import com.example.backendtrainticketsystem.model.Reservation;
import com.example.backendtrainticketsystem.model.Route;
import com.example.backendtrainticketsystem.model.User;
import com.example.backendtrainticketsystem.repository.ReservationRepository;
import com.example.backendtrainticketsystem.repository.RouteRepository;
import com.example.backendtrainticketsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.backendtrainticketsystem.common.ExceptionMessages.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final LocalTime SEVEN_THIRTY = LocalTime.of(7, 30, 0);
    private final LocalTime NINE_THIRTY = LocalTime.of(9, 30, 0);
    private final LocalTime SIXTEEN = LocalTime.of(16, 0, 0);
    private final LocalTime NINETEEN_THIRTY = LocalTime.of(19, 30, 0);

    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReservationService(UserRepository userRepository,
                              RouteRepository routeRepository,
                              ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
        this.reservationRepository = reservationRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<ReservationDtoResponse> getAllReservationsForUser(String username) {

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(USER_DOES_NOT_EXIST));

        List<Reservation> reservations = reservationRepository.findAllByUser(user);

        return reservations
                .stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDtoResponse.class))
                .collect(Collectors.toList());
    }

    public ReservationDtoResponse changeReservation(int reservationId,
                                                    int routeId,
                                                    LocalDate newDateOfDeparture,
                                                    LocalTime timestampOfReservationChange) {
        Reservation reservationToChange = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new InvalidReservationIdException(INVALID_RESERVATION_ID));

        Route newRoute = routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteDoesNotExistException(ROUTE_DOES_NOT_EXIST));

        if (newDateOfDeparture.isBefore(LocalDate.now())) {
            throw new InvalidDateOfDeparture(INVALID_DATE_OF_DEPARTURE);
        }

        if (newDateOfDeparture.isEqual(reservationToChange.getDateOfDeparture()) &&
        newRoute.getRouteId() == reservationToChange.getRoute().getRouteId()) {
            throw new MatchingRouteException(MATCHING_ROUTE);
        }

        if (newDateOfDeparture.isEqual(reservationToChange.getDateOfDeparture())) {
            long minutesBeforeTimeOfDepartureOfNewRoute
                    = ChronoUnit.MINUTES.between(timestampOfReservationChange, newRoute.getTimeOfDeparture());

            if (minutesBeforeTimeOfDepartureOfNewRoute < 30) {
                throw new TooLateToChangeReservation(TOO_LATE_TO_CHANGE_RESERVATION);
            }
        }

        if (checkForAvailableSeats(reservationToChange.getTicketCount(), newRoute,
                newDateOfDeparture)) {
            throw new NotEnoughAvailableSeatsException(NOT_ENOUGH_AVAILABLE_SEATS);
        }

        reservationToChange.setDateOfDeparture(newDateOfDeparture);

        return modelMapper.map(reservationRepository.save(reservationToChange),
                ReservationDtoResponse.class);

    }

    public List<ReservationDtoResponse> removeReservation(String username,
                                                          int reservationId) {

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(USER_DOES_NOT_EXIST));

        Reservation reservationToRemove = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new InvalidReservationIdException(INVALID_RESERVATION_ID));

        reservationRepository.delete(reservationToRemove);

        List<Reservation> reservations = reservationRepository.findAllByUser(user);

        return reservations
                .stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDtoResponse.class))
                .collect(Collectors.toList());
    }

    public ReservationDtoResponse createReservation(int routeId, String username,
                                  ReservationDto reservationDto) {

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteDoesNotExistException(ROUTE_DOES_NOT_EXIST));

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(USER_DOES_NOT_EXIST));

        if (reservationDto.getDateOfDeparture().isBefore(LocalDate.now())) {
            throw new InvalidDateOfDeparture(INVALID_DATE_OF_DEPARTURE);
        }

        if (checkForAvailableSeats(reservationDto.getTicketCount(), route,
                reservationDto.getDateOfDeparture())) {
            throw new NotEnoughAvailableSeatsException(NOT_ENOUGH_AVAILABLE_SEATS);
        }

        double ticketPrice = getTicketPrice(route, user, reservationDto);

        BigDecimal bd = new BigDecimal(ticketPrice).setScale(2, RoundingMode.HALF_UP);
        ticketPrice = bd.doubleValue();

        Reservation reservationToBeCreated = modelMapper.map(reservationDto, Reservation.class);

        reservationToBeCreated.setUser(user);
        reservationToBeCreated.setRoute(route);
        reservationToBeCreated.setDateOfCreation(LocalDateTime.now());
        reservationToBeCreated.setPrice(ticketPrice);

        return modelMapper.map(reservationRepository.save(reservationToBeCreated), ReservationDtoResponse.class);
    }

    private boolean checkForAvailableSeats(int ticketCount,
                                           Route route,
                                           LocalDate dateOfDeparture) {

        List<Reservation> currentReservationsForSelectedParameters
                        = reservationRepository.getAllByRouteAndDateOfDeparture(route, dateOfDeparture);
        int reservationsForSelectedRoute = 0;

        for (Reservation reservation : currentReservationsForSelectedParameters) {
            reservationsForSelectedRoute += reservation.getTicketCount();
        }

        int availableSeats = route.getTrain().getTotalSeats() - reservationsForSelectedRoute;

        return availableSeats < ticketCount;
    }
    public double getTicketPrice(Route route,
                                  User user,
                                  ReservationDto reservationDto) {

        double ticketPriceAfterTimeOfDepartureDiscount
                = calculateTicketPriceBasedOnRouteDepartureTime(route, route.getPrice());

        double ticketPriceAfterCardDiscountIsApplied =
                calculateTicketPriceBasedOnCardType(ticketPriceAfterTimeOfDepartureDiscount,
                                                    user,
                                                    reservationDto);

        return calculateTicketPriceBasedOnTicketType(
                reservationDto.getTicketType(),
                ticketPriceAfterCardDiscountIsApplied);
    }

    public double calculateTicketPriceBasedOnRouteDepartureTime(Route route,
                                                                 double ticketPrice) {

        LocalTime timeOfDeparture = route.getTimeOfDeparture();

        boolean ticketPurchaseTimeIsAfterSevenThirty =
                timeOfDeparture.isAfter(SEVEN_THIRTY);
        boolean ticketPurchaseTimeIsAtSevenThirty =
                timeOfDeparture.equals(SEVEN_THIRTY);
        boolean ticketPurchaseTimeIsBeforeNineThirty =
                timeOfDeparture.isBefore(NINE_THIRTY);

        boolean ticketPurchaseTimeIsAfterSixteen =
                timeOfDeparture.isAfter(SIXTEEN);
        boolean ticketPurchaseTimeIsAtSixteen =
                timeOfDeparture.equals(SIXTEEN);
        boolean ticketPurchaseTimeIsBeforeNineteenThirty =
                timeOfDeparture.isBefore(NINETEEN_THIRTY);

        boolean discountIsApplicable = ((!ticketPurchaseTimeIsAfterSevenThirty && !ticketPurchaseTimeIsAtSevenThirty)
                || !ticketPurchaseTimeIsBeforeNineThirty) &&
                ((!ticketPurchaseTimeIsAfterSixteen && !ticketPurchaseTimeIsAtSixteen)
                        || !ticketPurchaseTimeIsBeforeNineteenThirty);

        return discountIsApplicable ? (ticketPrice - (ticketPrice * 0.05)) : ticketPrice;
    }

    public double calculateTicketPriceBasedOnTicketType(TicketType ticketType,
                                                         double ticketPriceAfterCardDiscount) {
        if (ticketType == TicketType.ONE_WAY) {
            return ticketPriceAfterCardDiscount;
        } else {
            return ticketPriceAfterCardDiscount * 2;
        }
    }
    public double calculateTicketPriceBasedOnCardType(double ticketPrice,
                                                      User user,
                                                      ReservationDto reservationDto) {
        int ticketsCount = reservationDto.getTicketCount();
        double priceAfterDiscount = ticketPrice * reservationDto.getTicketCount();

        if (user.getDiscountCardType().equals(DiscountCardType.FAMILY)) {
            if (reservationDto.getChildUnderSixteen()) {
                priceAfterDiscount =
                        (ticketPrice * ticketsCount) - ((ticketPrice * ticketsCount) * 0.5);
            } else {
                priceAfterDiscount =
                        (ticketPrice * ticketsCount) - ((ticketPrice * ticketsCount) * 0.1);
            }
        } else if (user.getDiscountCardType().equals(DiscountCardType.ELDERLY)) {
            if (reservationDto.getTicketCount() == 1) {
                priceAfterDiscount = ticketPrice - (ticketPrice * 0.34);
            } else {
                priceAfterDiscount = (ticketPrice - (ticketPrice * 0.34))
                        + (--ticketsCount * ticketPrice);
            }
        }

        return priceAfterDiscount;
    }



}
