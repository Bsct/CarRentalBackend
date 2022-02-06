package com.example.carrental.mapper;

import com.example.carrental.model.Reservation;
import com.example.carrental.model.dto.NewReservationDto;
import com.example.carrental.model.dto.ReservationDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CarMapper.class, RentPickupMapper.class, RentReturnMapper.class, UserMapper.class}
        , injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReservationMapper {

    ReservationDto mapReservationToDto(Reservation reservation);

    Reservation mapFromNewReservation(NewReservationDto reservationDto);
}
