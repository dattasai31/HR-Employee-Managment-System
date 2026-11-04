package com.example.demo.DTO;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {
	@NotBlank(message="Employee name cannot be empty")
	private String empName;
	
	@NotBlank(message="Mobile number cannot be empty")
	@Pattern(regexp = "^[6-9][0-9]{9}$",message="Please enter valid 10 digit mobile number")
	private String mobileNumber;
	
	@NotBlank(message="email cannot be empty")
	@Email(message="Please enter valid email ")
	private String email;
	
	@NotBlank(message="Password cannot be empty")
	@Size(min=8,message="Password must be atleast 8 characters")
	private String password;
	
	@NotBlank(message="Designation cannot be empty")
	private String designation;
	
	@NotNull(message="Salary cannot be empty")
	@PositiveOrZero(message="Salary cannot be negative")
	private Double salary;
	
	@NotNull(message="Joining date cannot be empty")
	private LocalDate joinDate;
	
	@NotNull(message="Department ID cannot be empty")
	private Long deptId;
}
