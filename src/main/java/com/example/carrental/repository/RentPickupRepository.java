package com.example.carrental.repository;

import com.example.carrental.model.RentPickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentPickupRepository extends JpaRepository<RentPickup, Long> {
}
