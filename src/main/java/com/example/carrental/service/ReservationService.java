package com.example.carrental.service;

import com.example.carrental.mapper.RentPickupMapper;
import com.example.carrental.mapper.RentReturnMapper;
import com.example.carrental.mapper.ReservationMapper;
import com.example.carrental.model.ApplicationUser;
import com.example.carrental.model.Car;
import com.example.carrental.model.RentReturn;
import com.example.carrental.model.Reservation;
import com.example.carrental.model.dto.NewReservationDto;
import com.example.carrental.model.dto.RentPickupDto;
import com.example.carrental.model.dto.RentReturnDto;
import com.example.carrental.model.dto.ReservationDto;
import com.example.carrental.repository.ApplicationUserRepository;
import com.example.carrental.repository.CarRepository;
import com.example.carrental.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final ReservationMapper reservationMapper;
    private final RentPickupMapper rentPickupMapper;
    private final RentReturnMapper rentReturnMapper;
    private final ApplicationUserRepository userRepository;

    public List<ReservationDto> findAllReservations() {
        List<ReservationDto> reservationList = reservationRepository.findAll()
                .stream().map(reservationMapper::mapReservationToDto).collect(Collectors.toList());
        log.info("GetAll: " + reservationList);
        return reservationList;
    }


    public ReservationDto getReservationDtoById(Long id) {
        return reservationRepository.findById(id).map(reservationMapper::mapReservationToDto)
                .orElseThrow(() -> new EntityNotFoundException("Not found " + id));
    }

    @Transactional
    public void addCarToReservation(Long carId, NewReservationDto newReservationDto) {
        Reservation reservation = reservationMapper.mapFromNewReservation(newReservationDto);
        carRepository.findById(carId)
                .map(car -> getReservation(reservation, car))
                .ifPresent(reservationRepository::save);

    }

    private Reservation getReservation(Reservation reservation, Car car) {
        String principal = (String) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        Optional<ApplicationUser> byUsername = userRepository.findByUsername(principal);
        reservation.setUser(byUsername.get());
        reservation.setCar(car);

        long diff = ChronoUnit.DAYS.between(reservation.getRentDateFrom(), reservation.getRentDateTo());
        reservation.setPrice(car.getPrice() * diff);
        return reservation;
    }

    @Transactional
    public void addRentPickup(Long reservationId, RentPickupDto rentPickupDto) {
        Reservation reservation = reservationRepository.getById(reservationId);
        if (reservation.getRentPickup() != null) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Rent pickup already exists.");
        } else {
            reservation.setRentPickup(rentPickupMapper.mapDtoToRentPickup(rentPickupDto));
        }
        reservation.getCar().setRented(true);
        String principal = (String) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        Optional<ApplicationUser> byUsername = userRepository.findByUsername(principal);
        reservation.getRentPickup().setUser(byUsername.get());
        reservationRepository.save(reservation);
    }

    @Transactional
    public void addRentReturn(Long reservationId, RentReturnDto rentReturnDto) {
        Reservation reservation = reservationRepository.getById(reservationId);
        RentReturn rentReturn = rentReturnMapper.mapDtoToRentReturn(rentReturnDto);
        if (reservation.getRentReturn() != null) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Rent return already exists.");
        } else {
            reservation.setRentReturn(rentReturn);
        }
        reservation.setPrice(reservation.getPrice() + rentReturn.getSurchargeFee());
        reservation.getCar().setRented(false);
        String principal = (String) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        Optional<ApplicationUser> byUsername = userRepository.findByUsername(principal);
        reservation.getRentReturn().setUser(byUsername.get());
        reservationRepository.save(reservation);
    }

}
