package com.example.demo.DTO;

import java.time.LocalDate;

import com.example.demo.Entity.Employee;
import com.example.demo.Enum.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestDTO {
	@NotBlank(message="Leave type cannot be empty")
	private String leaveType;
	
	@NotBlank(message="Reason cannot be empty")
	private String reason;
	
	@NotNull(message="Please enter from date")
	@FutureOrPresent(message="From date cannot be in the past")
	private LocalDate startDate;
	
	@NotNull(message="Please enter to date")
	private LocalDate endDate;

	@NotNull(message = "Employee ID cannot be empty")
	private Long empId;

}
