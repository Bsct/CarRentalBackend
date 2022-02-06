package com.example.carrental.mapper;

import com.example.carrental.model.RentReturn;
import com.example.carrental.model.dto.RentReturnDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RentReturnMapper {

    @Mapping(target = "employeeSurname", source = "user.surname")
    @Mapping(target = "employeeName", source = "user.name")
    RentReturnDto map(RentReturn rentReturn);

    @Mapping(target = "user.surname", source = "employeeSurname")
    @Mapping(target = "user.name", source = "employeeName")
    RentReturn mapDtoToRentReturn(RentReturnDto rentReturnDto);
}
