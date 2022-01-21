package com.example.carrental.controller;

import com.example.carrental.model.Car;
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
    public List<Car> get() {
        return carService.findAllCars();
    }

    @CrossOrigin
    @PostMapping("")
    public void add(@RequestBody Car car) {
        carService.add(car);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public Car getById(@PathVariable Long id){
        return carService.findById(id);
    }
}
