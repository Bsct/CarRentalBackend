package com.example.carrental.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RentPickupDto {
    private LocalDate rentPickupDate;
    private String clientFeedback;
    private String employeeName;
    private String employeeSurname;
}
