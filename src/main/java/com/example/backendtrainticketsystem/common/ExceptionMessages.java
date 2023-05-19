package com.example.backendtrainticketsystem.common;

public class ExceptionMessages {
    public static final String USER_DOES_NOT_EXIST = "Such user does not exist!";
    public static final String ROUTE_DOES_NOT_EXIST = "Such route does not exist!";
    public static final String NOT_ENOUGH_AVAILABLE_SEATS
            = "Not enough available seats for this route!";
    public static final String USER_WITH_GIVEN_USERNAME_EXISTS
            = "User with this username already exists!";
    public static final String USER_WITH_GIVEN_EMAIL_EXISTS
            = "User with this email already exists!";
    public static final String NO_TRAINS_AVAILABLE_FOR_SELECTED_PLACE_OF_ARRIVAL
            = "No trains are available for the selected place of arrival!";
    public static final String NO_TRAINS_AVAILABLE_FOR_SELECTED_PLACE_OF_ARRIVAL_AND_TIME_OF_DEPARTURE
            = "No trains are available for the selected place of arrival and time of departure!";
    public static final String INVALID_RESERVATION_ID
            = "Reservation with this id does not exist!";
    public static final String INVALID_DATE_OF_DEPARTURE
            = "Date of departure cannot be a past date!";
    public static final String MATCHING_ROUTE
            = "New route is the same as the old one!";
    public static final String NOT_AN_ADMIN
            = "Only an admin can access this functionality!";
    public static final String TOO_LATE_TO_CHANGE_RESERVATION
            = "Your reservation cannot be changed because the desired train leaves in 30 minutes!";
}
