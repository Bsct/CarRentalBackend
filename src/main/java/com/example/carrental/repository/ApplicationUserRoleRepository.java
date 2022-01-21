package com.example.carrental.repository;

import com.example.carrental.model.ApplicationUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationUserRoleRepository extends JpaRepository<ApplicationUserRole, Long> {
    boolean existsByName(String name);
    Optional<ApplicationUserRole> findByName(String name);
}
