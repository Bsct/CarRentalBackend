package com.example.carrental.mapper;

import com.example.carrental.model.Client;
import com.example.carrental.model.dto.ClientDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CarMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ClientMapper {

    @Mapping(target = "username", source = "user.username")
    ClientDto map(Client client);
}
