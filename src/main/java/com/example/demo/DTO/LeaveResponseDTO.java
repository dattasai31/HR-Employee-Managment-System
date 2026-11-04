package com.example.demo.DTO;

import java.time.LocalDate;

import com.example.demo.Enum.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponseDTO {
	private Long empId;
	private Long leaveId;
	private String leaveType;
	private String reason;
	private LocalDate startDate;
	private LocalDate endDate;
	private Status status;

}
