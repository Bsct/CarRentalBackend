package com.example.carrental.controller;

import com.example.carrental.model.RentPickup;
import com.example.carrental.model.RentReturn;
import com.example.carrental.model.Reservation;
import com.example.carrental.model.dto.ReservationDto;
import com.example.carrental.service.ApplicationUserService;
import com.example.carrental.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final ApplicationUserService applicationUserService;


    @PostMapping("/add/{carId}")
    public void saveNewReservation(@RequestBody Reservation reservation, @PathVariable Long carId) {
        reservationService.addCarToReservation(carId, reservation);
    }

    @GetMapping("/{reservationId}")
    public Reservation getReservationById(@PathVariable Long reservationId) {
        return reservationService.getById(reservationId);
    }

    @PostMapping("/{reservationId}/pickup")
    public void addRentPickupToReservation(@RequestBody RentPickup rentPickup, @PathVariable Long reservationId) {
        reservationService.addRentPickup(reservationId, rentPickup);
    }

    @PostMapping("/{reservationId}/return")
    public void addRentReturnToReservation(@RequestBody RentReturn rentReturn, @PathVariable Long reservationId) {
        reservationService.addRentReturn(reservationId, rentReturn);
    }

    @GetMapping("")
    public List<ReservationDto> getAll(Principal principal) {
        Optional<Long> usedIdOptional = applicationUserService.getLoggedInUserId(principal);
        log.info("Logged in?: " + usedIdOptional);
        return reservationService.findAllReservations();
    }
}
