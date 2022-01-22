package com.example.carrental.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate reservationDate;
    private LocalDate rentDateFrom;
    private LocalDate rentDateTo;
    private double price;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "car_id")
    private Car car;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToOne(cascade = CascadeType.PERSIST)
    private RentPickup rentPickup;
    @OneToOne
    private RentReturn rentReturn;

}
