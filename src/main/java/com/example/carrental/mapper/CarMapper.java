package com.example.carrental.mapper;

import com.example.carrental.model.Car;
import com.example.carrental.model.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(target = "year", source = "productionYear")
    CarDto mapCarToDto(Car car);

    @Mapping(target = "productionYear", source = "year")
    Car mapDtoToCar(CarDto carDto);
}
