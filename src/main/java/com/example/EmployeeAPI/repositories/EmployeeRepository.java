package com.example.EmployeeAPI.repositories;

import com.example.EmployeeAPI.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}
