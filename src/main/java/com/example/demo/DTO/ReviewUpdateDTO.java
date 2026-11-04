package com.example.demo.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateDTO {
	@NotNull(message = "Rating cannot be empty")
	@DecimalMin(value = "1.0", message = "Rating must be at least 1")
	@DecimalMax(value = "5.0", message = "Maximum rating is 5.0")
	private Double rating;
	private String remarks;
	@NotNull(message = "Review date cannot be empty")
	private LocalDate reviewDate;


}
