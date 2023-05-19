package com.example.backendtrainticketsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trains")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id")
    private Long trainId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "total_seats", nullable = false)
    private int totalSeats;

    @OneToOne(mappedBy = "train")
    private Route route;
}
