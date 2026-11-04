package com.example.demo.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ReviewRequestDTO;
import com.example.demo.DTO.ReviewUpdateDTO;
import com.example.demo.Service.ReviewServiceImplementation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/review")
@Validated
public class ReviewController {
	@Autowired
	ReviewServiceImplementation service;
	
	
	@Operation(summary = "Add review",description = "Add new review")
	@ApiResponse(responseCode = "202",description = "Review added sucessfull")
	@PostMapping("/add")
	public ResponseEntity<?> addRating(@RequestBody @Valid ReviewRequestDTO reqdto)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.addRating(reqdto));
	}
	
	
	@Operation(summary = "Update the rating",description = "Update employee rating by review ID")
	@ApiResponse(responseCode = "200",description = "Updating rating sucessfull")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateRating(@PathVariable Long id,@RequestBody @Valid ReviewUpdateDTO redto)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.updateRating(id, redto));
	}
	
	
	@Operation(summary = "View all ratings",description = "Get all reviews")
	@ApiResponse(responseCode = "200",description = "Getting all reviews successfull")
	@GetMapping("/view")
	public ResponseEntity<?> getAll()
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
	}
	
	
	@Operation(summary = "Find review by review ID",description = "Find review by review ID")
	@ApiResponse(responseCode = "200",description = "Fetching review by review ID successfull")
	@GetMapping("/find/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
	}
	
	
	@Operation(summary = "All reviews of an employee",description = "Find all reviews of an employee using employee ID")
	@ApiResponse(responseCode = "200",description = "Fetching reviews of an employee successfull")
	@GetMapping("/findbyemp/{id}")
	public ResponseEntity<?> getByEmpid(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getByEmpid(id));
	}
	
	
	@Operation(summary = "Review by date",description = "Find all reviews on a particular date")
	@ApiResponse(responseCode = "200",description = "Fetching revies on a particular date is successfull")
	@GetMapping("/findbydate/{date}")
	public ResponseEntity<?> getByDate(@PathVariable @DateTimeFormat(iso =DateTimeFormat.ISO.DATE) LocalDate date)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getByDate(date));
	}
	
	
	@Operation(summary = "Average rating of an employee",description = "Get average rating of an employee")
	@ApiResponse(responseCode = "200",description = "Fetching average rating of an employee is successfull")
	@GetMapping("/averagerating/{id}")
	public ResponseEntity<?> getAverageRating(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.averageRatingOfAnEmployee(id));
	}
	
	
	@Operation(summary = "Average rating of a department",description = "Get average rating of department")
	@ApiResponse(responseCode = "200",description = "Fetching average rating of a department is successfull")
	@GetMapping("/averageratingdept/{id}")
	public ResponseEntity<?> getAverageDeptRating(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.averageRatingDepartment(id));
	}
	
	
	@Operation(summary = "Delete a review",description = "Delete a review by review ID")
	@ApiResponse(responseCode = "200",description = "Deleting review by review ID successfull")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteById(id));
	}

}
