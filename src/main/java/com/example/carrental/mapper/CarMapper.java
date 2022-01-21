/*
package com.example.carrental.mapper;

import com.example.carrental.dto.CarDto;
import com.example.carrental.model.Car;

public class CarMapper {

    public static Car mapToEntity(CarDto carDto){
        Car carEntity = new Car();
        carEntity.setManufacturer(carDto.getManufacturer());
        carEntity.setModel(carEntity.getModel());
        carEntity.setProductionYear(carDto.getProductionYear());
        carEntity.setEngineSize(carDto.getEngineSize());
        carEntity.setColor(carDto.getColor());
        carEntity.setPrice(carDto.getPrice());
        carEntity.setRented(carDto.isRented());
        return carEntity;
    }

    public static CarDto mapToDto(Car carEntity){
        return CarDto.builder()
                .id(carEntity.getId())
                .manufacturer(carEntity.getManufacturer())
                .model(carEntity.getModel())
                .productionYear(carEntity.getProductionYear())
                .engineSize(carEntity.getEngineSize())
                .color(carEntity.getColor())
                .price(carEntity.getPrice())
                .rented(carEntity.isRented())
                .build();
    }
}
*/
