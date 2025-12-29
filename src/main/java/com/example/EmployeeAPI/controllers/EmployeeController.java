package com.example.EmployeeAPI.controllers;

import com.example.EmployeeAPI.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    @GetMapping(path = "/{employeeID}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeID) {
        return new EmployeeDTO(
                employeeID,
                "Umesh",
                "umesh@gmail.com",
                27,
                LocalDate.of(2025, 3, 12),
                false
        );
    }

    @GetMapping
    public String getAllEmployee(@RequestParam(required = false) Integer age,
                                 @RequestParam(required = false) String sortBy) {
        return "Hi age " + age + " " + sortBy;
    }

}
