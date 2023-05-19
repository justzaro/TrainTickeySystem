package com.example.backendtrainticketsystem.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteDtoResponse {
    @NotBlank
    private Long routeId;
    @NotBlank
    private String placeOfDeparture;
    @NotBlank
    private String placeOfArrival;
    @NotBlank
    private LocalTime timeOfDeparture;
    @NotBlank
    private LocalTime timeOfArrival;
    @NotBlank
    private double price;
}
