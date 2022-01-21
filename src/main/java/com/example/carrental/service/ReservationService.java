package com.example.carrental.service;

import com.example.carrental.model.Car;
import com.example.carrental.model.Reservation;
import com.example.carrental.repository.CarRepository;
import com.example.carrental.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;

    public void add(Reservation reservation){
        reservationRepository.save(reservation);
    }

    public void delete(Reservation reservation){
        reservationRepository.delete(reservation);
    }


    public Reservation addCarToReservation(Long carId, Reservation reservation){
        Optional<Car> carOptional = carRepository.findById(carId);
        if (carOptional.isPresent()){
            Car car = carOptional.get();
            reservation.setCar(car);
            car.setRented(true);
            long diff = ChronoUnit.DAYS.between(reservation.getRentDateFrom(), reservation.getRentDateTo());
            reservation.setPrice(car.getPrice() * diff);
            return reservation;
        }
        return null;
    }


    public List<Reservation> findAllReservations(){
        List<Reservation> reservationList = reservationRepository.findAll();
        log.info("GetAll: " + reservationList);
        return reservationList;
    }



    public Reservation getById(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()){
            return reservation.get();
        }
        throw new EntityNotFoundException("Not found " + id);
    }
}
