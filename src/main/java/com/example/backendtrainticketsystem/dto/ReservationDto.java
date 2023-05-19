package com.example.backendtrainticketsystem.dto;

import com.example.backendtrainticketsystem.common.enums.TicketType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    @NotNull
    private int ticketCount;
    @NotNull
    private TicketType ticketType;
    @NotNull
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 17, message = "To be a considered a child, age must be below 18")
    private int childAge;
    @NotNull
    private Boolean childUnderSixteen;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfDeparture;
}
