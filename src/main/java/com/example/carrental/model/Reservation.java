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
    @OneToOne
    private Car car;
    @OneToOne
    private Employee employeeReturn;
    @OneToOne
    private Employee employeePickup;
    @OneToOne
    private Client client;
    @OneToOne
    private RentPickup rent;

}
