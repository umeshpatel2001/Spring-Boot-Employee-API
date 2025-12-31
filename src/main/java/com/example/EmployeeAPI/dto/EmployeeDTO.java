package com.example.EmployeeAPI.dto;

import com.example.EmployeeAPI.annotations.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    @NotNull(message = "Required field in Employee: name")
    @NotEmpty(message = "Name of Employee can not be empty")
    @NotBlank(message = "Name of Employee can not be Blank")
    @Size(min = 2, max = 10, message = "Name should be in the range 2 - 10")
    private String name;
    @Email(message = "Email should be valid email")
    private String email;
    @Max(value = 80, message = "Age can not be more then 80")
    @Min(value = 18, message = "Age cannot be less then 18")
    private Integer age;
    @PastOrPresent(message = "Date of joining can not be in future")
    private LocalDate dateOfJoining;
    @JsonProperty("isActive")
    private Boolean isActive;
    @NotBlank(message = "Role can not be null")
    @EmployeeRoleValidation
    private String role; // ADMIN, USER
}
