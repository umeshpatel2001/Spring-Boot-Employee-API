package com.example.EmployeeAPI.controllers;

import com.example.EmployeeAPI.dto.EmployeeDTO;
import com.example.EmployeeAPI.exception.ResourceNotFoundException;
import com.example.EmployeeAPI.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    final private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/{employeeID}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeID) {
        Optional<EmployeeDTO> employeeDTO = employeeService.findById(employeeID);
        return employeeDTO.map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1)).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: "+ employeeID));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee(@RequestParam(required = false) Integer age,
                                                            @RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> creteNewEmployee(@RequestBody @Valid EmployeeDTO inputEntity) {
        EmployeeDTO employeeDTO = employeeService.createNewEmployee(inputEntity);
        return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId, employeeDTO));
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId) {
        boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
        if (gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates,
                                                                 @PathVariable Long employeeId) {
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(employeeId, updates);
        if (employeeDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }

}
