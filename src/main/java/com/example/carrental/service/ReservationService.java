package com.example.carrental.service;

import com.example.carrental.mapper.ReservationMapper;
import com.example.carrental.model.*;
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
    private final ApplicationUserService userService;
    private final ApplicationUserRepository userRepository;

    public List<ReservationDto> findAllReservations() {
        List<ReservationDto> reservationList = reservationRepository.findAll()
                .stream().map(reservationMapper::map).collect(Collectors.toList());
        log.info("GetAll: " + reservationList);
        return reservationList;
    }


    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found " + id));
    }

    public void add(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public void delete(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    @Transactional
    public void addCarToReservation(Long carId, Reservation reservation) {
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
    public void addRentPickup(Long reservationId, RentPickup rentPickup) {
        Reservation reservation = getById(reservationId);
        if (reservation.getRentPickup() != null) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Rent pickup already exists.");
        } else {
            reservation.setRentPickup(rentPickup);
        }
        reservation.getCar().setRented(true);
        String principal = (String) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        Optional<ApplicationUser> byUsername = userRepository.findByUsername(principal);
        reservation.getRentPickup().setUser(byUsername.get());
        reservationRepository.save(reservation);
    }

    @Transactional
    public void addRentReturn(Long reservationId, RentReturn rentReturn) {
        Reservation reservation = getById(reservationId);
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
