package com.example.EmployeeAPI.services;

import com.example.EmployeeAPI.dto.EmployeeDTO;
import com.example.EmployeeAPI.entities.EmployeeEntity;
import com.example.EmployeeAPI.exception.ResourceNotFoundException;
import com.example.EmployeeAPI.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public Optional<EmployeeDTO> findById(Long employeeID) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeID);
        return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();
        return employeeEntityList
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEntity) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEntity, EmployeeEntity.class);
        EmployeeEntity employeeEntity = employeeRepository.save(toSaveEntity);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
        isExistById(employeeId);
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity saveEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(saveEmployeeEntity, EmployeeDTO.class);
    }

    public void isExistById(Long employeeId) {
        boolean exist = employeeRepository.existsById(employeeId);
        if (!exist) throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
    }

    public Boolean deleteEmployeeById(Long employeeId) {
        isExistById(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
        isExistById(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
}
