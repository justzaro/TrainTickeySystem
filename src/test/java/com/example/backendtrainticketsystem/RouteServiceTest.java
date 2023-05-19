package com.example.backendtrainticketsystem;

import com.example.backendtrainticketsystem.dto.RouteDto;
import com.example.backendtrainticketsystem.dto.RouteDtoResponse;
import com.example.backendtrainticketsystem.exception.NoTrainsAvailableForGivenPlaceOfArrivalAndTimeOfDeparture;
import com.example.backendtrainticketsystem.model.Route;
import com.example.backendtrainticketsystem.repository.RouteRepository;
import com.example.backendtrainticketsystem.service.RouteService;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouteServiceTest {

    @Autowired
    RouteService routeService;
    @Autowired
    RouteRepository routeRepository;

    //Test Case 2 - getRoutesByPlaceOfArrivalAndTimeOfDeparture()
    @Test(expected = NoTrainsAvailableForGivenPlaceOfArrivalAndTimeOfDeparture.class)
    public void should_throw_no_trains_available_exception() {
        RouteDto routeDto = new RouteDto();

        routeDto.setPlaceOfArrival("Tarnovo");
        routeDto.setTimeOfDeparture(LocalTime.of(8, 30));

        routeService.getRoutesByPlaceOfArrivalAndTimeOfDeparture(routeDto);
    }

    //Test Case 1 - getRoutesByPlaceOfArrivalAndTimeOfDeparture()
    @Test
    public void should_contain_only_one_route() {
        RouteDto routeDto = new RouteDto();

        //Setting 'place of arrival' to Tarnovo and 'time of departure' to 08:30:00
        //Assures that routes List will contain at least one object - thus be TRUE
        routeDto.setPlaceOfArrival("Sevlievo");
        routeDto.setTimeOfDeparture(LocalTime.of(8, 30));

        List<Route> routes
                = routeRepository.findAllByPlaceOfArrivalAndTimeOfDeparture(
                        routeDto.getPlaceOfArrival(),
                        routeDto.getTimeOfDeparture());

        assertEquals(routes.get(0).getRouteId(), 17, 0);
    }

}
