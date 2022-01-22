package com.example.carrental.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RentReturnDto {
    private LocalDate rentReturnDate;
    private double surchargeFee;
    private String clientFeedback;
    private String employeeName;
    private String employeeSurname;
}
