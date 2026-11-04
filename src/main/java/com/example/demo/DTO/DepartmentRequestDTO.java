package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestDTO {
	private Long deptId;
	@NotBlank(message="Department name cannot be empty")
	private String deptName;

}
