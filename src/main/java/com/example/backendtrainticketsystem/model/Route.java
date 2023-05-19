package com.example.backendtrainticketsystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Long routeId;

    @Column(name = "place_of_departure", nullable = false)
    private String placeOfDeparture;

    @Column(name = "place_of_arrival", nullable = false)
    private String placeOfArrival;

    @Column(name = "time_of_departure", nullable = false)
    private LocalTime timeOfDeparture;

    @Column(name = "time_of_arrival", nullable = false)
    private LocalTime timeOfArrival;

    @Column(name = "price", nullable = false)
    private double price;

    @OneToOne
    @JoinColumn(name = "train_id", referencedColumnName = "train_id", unique = true)
    private Train train;
}
