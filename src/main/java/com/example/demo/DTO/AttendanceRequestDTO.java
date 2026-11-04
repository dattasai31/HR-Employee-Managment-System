package com.example.demo.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequestDTO {
	@NotNull(message = "checkin cannot be empty")
	private Boolean checkIn;
	

}
