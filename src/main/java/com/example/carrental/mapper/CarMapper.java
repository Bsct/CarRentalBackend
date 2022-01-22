package com.example.carrental.mapper;

import com.example.carrental.model.Car;
import com.example.carrental.model.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(target = "year", source = "productionYear")
    CarDto mapCar(Car car);
}
