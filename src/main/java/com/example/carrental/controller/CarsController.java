package com.example.carrental.controller;

import com.example.carrental.model.dto.CarDto;
import com.example.carrental.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarsController {
    private final CarService carService;

    @CrossOrigin
    @GetMapping("")
    public List<CarDto> get() {
        return carService.findAllCars();
    }

    @CrossOrigin
    @PostMapping("")
    public void add(@RequestBody CarDto carDto) {
        carService.add(carDto);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public CarDto getById(@PathVariable Long id) {
        return carService.findCarDtoById(id);
    }
}
