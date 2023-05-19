package com.example.backendtrainticketsystem.controller;

import com.example.backendtrainticketsystem.dto.RouteDto;
import com.example.backendtrainticketsystem.dto.RouteDtoResponse;
import com.example.backendtrainticketsystem.dto.UserDtoResponse;
import com.example.backendtrainticketsystem.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/train-portal/routes")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping(path = "{placeOfArrival}/get-routes")
    public List<RouteDtoResponse> getRoutesByPlaceOfArrival(
            @PathVariable("placeOfArrival") String placeOfArrival) {
        return routeService.getRoutesByPlaceOfArrival(placeOfArrival);
    }

    @GetMapping(path = "/filter")
    public List<RouteDtoResponse> getRoutesByPlaceOfArrivalAndTimeOfDeparture(
            @Valid @RequestBody RouteDto routeDto) {
        return routeService.getRoutesByPlaceOfArrivalAndTimeOfDeparture(
                routeDto);
    }
}
