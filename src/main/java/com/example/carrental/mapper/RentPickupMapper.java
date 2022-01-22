package com.example.carrental.mapper;

import com.example.carrental.model.RentPickup;
import com.example.carrental.model.dto.RentPickupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RentPickupMapper {

    @Mapping(target = "employeeSurname", source = "employee.surname")
    @Mapping(target = "employeeName", source = "employee.name")
    RentPickupDto map(RentPickup rentPickup);
}
