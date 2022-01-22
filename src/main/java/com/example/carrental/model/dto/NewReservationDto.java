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
public class NewReservationDto {
    private LocalDate reservationDate;
    private LocalDate rentDateFrom;
    private LocalDate rentDateTo;
}
