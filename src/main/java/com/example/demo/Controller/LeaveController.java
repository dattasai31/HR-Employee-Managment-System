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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.LeaveApprovalDTO;
import com.example.demo.DTO.LeaveRequestDTO;
import com.example.demo.DTO.LeaveResponseDTO;
import com.example.demo.Enum.Status;
import com.example.demo.Service.LeaveServiceImplementation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/leave")
@Validated
public class LeaveController {
	@Autowired
	private LeaveServiceImplementation service;
	
	
	@Operation(summary = "Apply leave",description = "Apply for a leave")
	@ApiResponse(responseCode = "202",description = "Leave applied successfully")
	@PostMapping("/apply")
	public ResponseEntity<?> applyLeave(@RequestBody @Valid LeaveRequestDTO reqdto)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.applyLeave(reqdto));
	}
	
	
	@Operation(summary = "Approve/Reject leave",description = "Update the status of leave")
	@ApiResponse(responseCode = "200",description = "Updating status of leave successfull")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateLeave(@PathVariable Long id,@RequestBody @Valid LeaveApprovalDTO apdto)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.updateLeave(id, apdto));
	}
	
	
	@Operation(summary = "View all leaves",description = "Get all leaves")
	@ApiResponse(responseCode = "200",description = "Getting all leaves successfull")
	@GetMapping("/view")
	public ResponseEntity<?> getAll()
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
	}
	
	
	@Operation(summary = "Get leave by leave ID",description = "Find leave by leave ID")
	@ApiResponse(responseCode = "200",description = "Fetching leave records by leave ID successfull")
	@GetMapping("/find/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
	}
	
	
	@Operation(summary = "Leaves of an employee",description = "Find all leaves of an employee")
	@ApiResponse(responseCode = "200",description = "Fetching leaves of an employee successfull")
	@GetMapping("/findbyemp/{id}")
	public ResponseEntity<?> getByEmpId(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getByEmpid(id));
	}
	
	
	@Operation(summary = "Delete leave record",description = "Delete leave record using leave ID")
	@ApiResponse(responseCode = "200",description = "Deleting leave record successfull")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteLeaveById(id));
	}
	
	
	@Operation(summary = "Fetch Applied/Approved/Rejected leaves",description = "Fetch all Pending/Applied/Approved leaves")
	@ApiResponse(responseCode = "200",description = "Fetching  leaves successfull")
	@GetMapping("/status/{status}")
	public ResponseEntity<?> appliedLeave(@PathVariable Status status)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.appliedLeaves(status));
	}

}
