package com.example.carrental.mapper;

import com.example.carrental.model.Reservation;
import com.example.carrental.model.dto.ReservationDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CarMapper.class, ClientMapper.class, RentPickupMapper.class, RentReturnMapper.class}
        , injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReservationMapper {

    ReservationDto map(Reservation reservation);
}