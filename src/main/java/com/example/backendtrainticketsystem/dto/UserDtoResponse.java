package com.example.backendtrainticketsystem.dto;

import com.example.backendtrainticketsystem.common.enums.DiscountCardType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    //add custom validation for local date
    private LocalDate dateOfBirth;
    @NotNull
    //add custom validation for enum
    private DiscountCardType discountCardType;

    private List<ReservationDtoResponse> reservations;
}
