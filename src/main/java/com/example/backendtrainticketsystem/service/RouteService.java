package com.example.backendtrainticketsystem.service;

import com.example.backendtrainticketsystem.dto.RouteDto;
import com.example.backendtrainticketsystem.dto.RouteDtoResponse;
import com.example.backendtrainticketsystem.exception.NoTrainsAvailableForGivenPlaceOfArrival;
import com.example.backendtrainticketsystem.exception.NoTrainsAvailableForGivenPlaceOfArrivalAndTimeOfDeparture;
import com.example.backendtrainticketsystem.model.Route;
import com.example.backendtrainticketsystem.repository.RouteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.backendtrainticketsystem.common.ExceptionMessages.*;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<RouteDtoResponse> getRoutesByPlaceOfArrivalAndTimeOfDeparture(
            RouteDto routeDto) {

        List<Route> routes = routeRepository.findAllByPlaceOfArrivalAndTimeOfDeparture(
                routeDto.getPlaceOfArrival(), routeDto.getTimeOfDeparture());

        if (routes.isEmpty()) {
            throw new NoTrainsAvailableForGivenPlaceOfArrivalAndTimeOfDeparture
                    (NO_TRAINS_AVAILABLE_FOR_SELECTED_PLACE_OF_ARRIVAL_AND_TIME_OF_DEPARTURE);
        }

        return routes
                    .stream()
                    .map(route -> modelMapper.map(route, RouteDtoResponse.class))
                    .collect(Collectors.toList());
    }
    public List<RouteDtoResponse> getRoutesByPlaceOfArrival(String placeOfArrival) {
        List<Route> routes = routeRepository.findAllByPlaceOfArrival(placeOfArrival);

        if (routes.isEmpty()) {
            throw new NoTrainsAvailableForGivenPlaceOfArrival(NO_TRAINS_AVAILABLE_FOR_SELECTED_PLACE_OF_ARRIVAL);
        }

        return routes
               .stream()
               .map(route -> modelMapper.map(route, RouteDtoResponse.class))
               .collect(Collectors.toList());
    }
}
