package com.example.backendtrainticketsystem.repository;

import com.example.backendtrainticketsystem.model.Reservation;
import com.example.backendtrainticketsystem.model.Route;
import com.example.backendtrainticketsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    int countReservationByUser(User user);
    List<Reservation> findAllByUser(User user);
    List<Reservation> getAllByRouteAndDateOfDeparture(Route route, LocalDate dateOfDeparture);
}
