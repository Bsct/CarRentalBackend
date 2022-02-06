package com.example.carrental.service;

import com.example.carrental.mapper.CarMapper;
import com.example.carrental.model.dto.CarDto;
import com.example.carrental.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public List<CarDto> findAllCars() {
        List<CarDto> carList = carRepository.findAll()
                .stream().map(carMapper::mapCarToDto).collect(Collectors.toList());
        log.info("GetAll: " + carList);
        return carList;
    }


    public CarDto findCarDtoById(Long id) {
        return carRepository.findById(id).map(carMapper::mapCarToDto)
                .orElseThrow(() -> new EntityNotFoundException("Not found " + id));
    }

    @Transactional
    public void add(CarDto carDto) {
        log.info("Add: " + carDto);
        carRepository.save(carMapper.mapDtoToCar(carDto));
    }
}
