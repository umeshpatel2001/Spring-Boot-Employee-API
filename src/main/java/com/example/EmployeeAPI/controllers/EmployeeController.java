package com.example.EmployeeAPI.controllers;

import com.example.EmployeeAPI.dto.EmployeeDTO;
import com.example.EmployeeAPI.services.EmployeeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    final private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/{employeeID}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeID) {
        return employeeService.findById(employeeID);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployee(@RequestParam(required = false) Integer age,
                                               @RequestParam(required = false) String sortBy) {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeDTO creteNewEmployee(@RequestBody EmployeeDTO inputEntity) {
        return employeeService.createNewEmployee(inputEntity);
    }

}
