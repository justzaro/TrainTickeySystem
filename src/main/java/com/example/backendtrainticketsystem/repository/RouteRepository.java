package com.example.backendtrainticketsystem.repository;

import com.example.backendtrainticketsystem.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    List<Route> findAllByPlaceOfArrival(String placeOfArrival);
    List<Route> findAllByPlaceOfArrivalAndTimeOfDeparture(String placeOfArrival,
                                                          LocalTime timeOfDeparture);
}
