package com.example.carrental.mapper;

import com.example.carrental.model.RentPickup;
import com.example.carrental.model.dto.RentPickupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RentPickupMapper {

    @Mapping(target = "employeeSurname", source = "user.surname")
    @Mapping(target = "employeeName", source = "user.name")
    RentPickupDto map(RentPickup rentPickup);
}
