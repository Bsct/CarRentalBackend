package com.example.carrental.model.dto;

import lombok.Data;

@Data
public class CarDto {
    private Long id;
    private String manufacturer;
    private String model;
    private int year;
    private double engineSize;
    private String color;
    private double price;
    private boolean rented;
}
