package com.example.carrental.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private Long id;
    private LocalDate reservationDate;
    private LocalDate rentDateFrom;
    private LocalDate rentDateTo;
    private double price;
    private CarDto car;
    private ApplicationUserDto user;
    private RentPickupDto rentPickup;
    private RentReturnDto rentReturn;
}
