package com.example.backendtrainticketsystem.model;

import com.example.backendtrainticketsystem.common.enums.TicketType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int reservationId;

    @Column(name = "ticket_count", nullable = false)
    private int ticketCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type")
    private TicketType ticketType;

    @Column(name = "child_is_under_sixteen")
    private boolean childIsUnderSixteen;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "date_of_creation", nullable = false)
    private LocalDateTime dateOfCreation;

    @Column(name = "date_of_departure", nullable = false)
    private LocalDate dateOfDeparture;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    private Route route;
}
