package com.example.carrental.mapper;

import com.example.carrental.model.ApplicationUser;
import com.example.carrental.model.dto.ApplicationUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "admin", ignore = true)
    ApplicationUserDto map(ApplicationUser applicationUser);
}
