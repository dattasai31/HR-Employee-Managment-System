package com.example.demo.DTO;

import com.example.demo.Enum.Status;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApprovalDTO {
	@NotNull(message = "Leave ID cannot be empty")
	private Long leaveId;
	@NotNull(message = "Status cannot be empty")
	private Status status;

}
