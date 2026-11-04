package com.example.demo.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponseDTO {
	private Long attendanceId;
	private Long empId;
	private String empName;
	private Boolean checkIn;
	private String attendanceStatus;
	private LocalDate checkinDate;
	

}
