package com.example.demo.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDTO {
	private Long deptId;
	private String deptName;
	 private List<EmployeeResponseDTO> employees;

}
