package com.example.demo.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.AttendanceRequestDTO;
import com.example.demo.Service.AttendanceServiceImplementation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/attendance")
@Validated
public class AttendanceController {
	@Autowired
	AttendanceServiceImplementation service;
	
	
	@Operation(summary = "Log in",description = "Used to checkin fro work")
	@ApiResponse(responseCode = "202",description = "checkin successfull")
	@PostMapping("/login/{id}")
	public ResponseEntity<?> login(@PathVariable Long id,@RequestBody @Valid AttendanceRequestDTO reqdto)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.logIn(id, reqdto));
	}
	
	
	@Operation(summary = "Get all attendance details",description = "Get all attendance records")
	@ApiResponse(responseCode = "200",description = "Fetching attendance records successfull")
	@GetMapping("/view")
	public ResponseEntity<?> getAll()
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
	}
	
	
	@Operation(summary = "Find attendance details by attendance ID",description = "Get the attendance details based on attendance ID")
	@ApiResponse(responseCode = "200",description = "Fetching attendance records by attendance ID successfull")
	@GetMapping("/find/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
	}
	
	
	@Operation(summary = "Find attendance details by employee ID",description = "Get all attendance details of an employee using employee ID")
	@ApiResponse(responseCode = "200",description = "Fetching attendance records by employee ID successfull")
	@GetMapping("/findbyemp/{id}")
	public ResponseEntity<?> getByEmpid(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getByEmpId(id));
	}
	
	
	@Operation(summary = "Delete the attendance record",description = "Deleting the attendance record")
	@ApiResponse(responseCode = "200",description = "Deleting attendance record successfull")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteById(id));
	}
	
	
	@Operation(summary = "Absentees record by date",description = "Get the absent employees on particular date")
	@ApiResponse(responseCode = "200",description = "Fetching absent employee details successfull")
	@GetMapping("/bydate/{date}")
	public ResponseEntity<?> getByDate(@PathVariable @DateTimeFormat(iso =DateTimeFormat.ISO.DATE) LocalDate date)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getAbsenteesByDate(date));
	}
	
	
	@Operation(summary = "Total present days of an employee",description = "Finding the total number of days employee is present between two dates")
	@ApiResponse(responseCode = "200",description = "Fetching total number of present days successfull")
	@GetMapping("/presentdays/{id}")
	public ResponseEntity<?> presentDays(@PathVariable Long id,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.presentDays(id, fromDate, toDate));
	}
	
	
	@Operation(summary = "Total attendance percentage of an employee",description = "Get the attendance percentage based on attendance ID")
	@ApiResponse(responseCode = "200",description = "Fetching attendance percentage successfull")
	@GetMapping("/percentage/{id}")
	public ResponseEntity<?> attendancePercentage(@PathVariable Long id,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.attendancePercentage(id, fromDate, toDate));
	}
	

}
