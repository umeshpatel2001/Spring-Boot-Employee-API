package com.example.EmployeeAPI.controllers;

import com.example.EmployeeAPI.entities.EmployeeEntity;
import com.example.EmployeeAPI.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    final private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(path = "/{employeeID}")
    public EmployeeEntity getEmployeeById(@PathVariable Long employeeID) {
        return employeeRepository.findById(employeeID).orElse(null);
    }

    @GetMapping
    public List<EmployeeEntity> getAllEmployee(@RequestParam(required = false) Integer age,
                                               @RequestParam(required = false) String sortBy) {
        return employeeRepository.findAll();
    }

    @PostMapping
    public EmployeeEntity creteNewEmployee(@RequestBody EmployeeEntity inputEntity) {
        return employeeRepository.save(inputEntity);
    }

}
