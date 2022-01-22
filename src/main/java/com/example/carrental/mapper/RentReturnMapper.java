package com.example.carrental.mapper;

import com.example.carrental.model.RentReturn;
import com.example.carrental.model.dto.RentReturnDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RentReturnMapper {

    @Mapping(target = "employeeSurname", source = "employee.surname")
    @Mapping(target = "employeeName", source = "employee.name")
    RentReturnDto map(RentReturn rentReturn);
}
