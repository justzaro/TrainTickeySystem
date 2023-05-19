package com.example.backendtrainticketsystem.dto;

import com.example.backendtrainticketsystem.common.enums.TicketType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDtoResponse {
    @NotNull
    private int reservationId;
    @NotNull
    private int ticketCount;
    @NotNull
    private TicketType ticketType;
    @NotNull
    private Boolean childUnderSixteen;
    @NotNull
    private double price;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateOfCreation;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfDeparture;
}
