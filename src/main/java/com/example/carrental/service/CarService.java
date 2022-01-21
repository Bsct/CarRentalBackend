package com.example.carrental.service;

import com.example.carrental.model.Car;
import com.example.carrental.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public List<Car> findAllCars(){
        List<Car> carList = carRepository.findAll();
        log.info("GetAll: " + carList);
        return carList;
    }


    public Car findById(Long id) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()){
            return car.get();
        }
        throw new EntityNotFoundException("Not found: " + id);
    }

    public void add(Car car){
        log.info("Add: " + car);
        carRepository.save(car);
    }
}
