package com.example.carrental.repository;

import com.example.carrental.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    boolean existsByUsername(String username);
    Optional<ApplicationUser> findByUsername(String username);
}
