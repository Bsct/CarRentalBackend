package com.example.carrental.repository;

import com.example.carrental.model.RentReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentReturnRepository extends JpaRepository<RentReturn, Long> {
}
