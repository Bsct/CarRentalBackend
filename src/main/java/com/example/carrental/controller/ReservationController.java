package com.example.carrental.controller;

import com.example.carrental.model.Reservation;
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
    public void add(@RequestBody Reservation reservation, @PathVariable Long carId){
        Reservation newReservation = reservationService.addCarToReservation(carId, reservation);
        reservationService.add(newReservation);
    }

    @GetMapping("/{reservationId}")
    public Reservation getReservationById(@PathVariable Long reservationId){
        return reservationService.getById(reservationId);
    }

    @GetMapping("")
    public List<Reservation> getAll(Principal principal) {
        Optional<Long> usedIdOptional = applicationUserService.getLoggedInUserId(principal);
        log.info("Logged in?: " + usedIdOptional);
        return reservationService.findAllReservations();
    }
}
