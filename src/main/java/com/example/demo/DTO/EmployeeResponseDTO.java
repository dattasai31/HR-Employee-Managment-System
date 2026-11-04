package com.example.demo.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {
	private Long empId;
	private String empName;
	private String designation;
	private Double salary;
	private Long deptId;
	private String deptName;
	private List<LeaveResponseDTO> leaves;
	private List<ReviewResponseDTO> review;
	private List<AttendanceResponseDTO> attendance;
	

}
