package com.example.carrental.controller;

import com.example.carrental.model.dto.NewReservationDto;
import com.example.carrental.model.dto.RentPickupDto;
import com.example.carrental.model.dto.RentReturnDto;
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
    public void saveNewReservation(@RequestBody NewReservationDto newReservationDto, @PathVariable Long carId) {
        reservationService.addCarToReservation(carId, newReservationDto);
    }

    @GetMapping("/{reservationId}")
    public ReservationDto getReservationById(@PathVariable Long reservationId) {
        return reservationService.getReservationDtoById(reservationId);
    }

    @PostMapping("/{reservationId}/pickup")
    public void addRentPickupToReservation(@RequestBody RentPickupDto rentPickupDto, @PathVariable Long reservationId) {
        reservationService.addRentPickup(reservationId, rentPickupDto);
    }

    @PostMapping("/{reservationId}/return")
    public void addRentReturnToReservation(@RequestBody RentReturnDto rentReturnDto, @PathVariable Long reservationId) {
        reservationService.addRentReturn(reservationId, rentReturnDto);
    }

    @GetMapping("")
    public List<ReservationDto> getAll(Principal principal) {
        Optional<Long> usedIdOptional = applicationUserService.getLoggedInUserId(principal);
        log.info("Logged in?: " + usedIdOptional);
        return reservationService.findAllReservations();
    }
}
