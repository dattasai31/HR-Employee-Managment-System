package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.DepartmentRequestDTO;
import com.example.demo.Service.DerpartmentServiceImplementation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/department")
@Validated
public class DepartmentController {
	@Autowired
	private DerpartmentServiceImplementation service;
	
	
	@Operation(summary = "Create/update  department",description = "Create/upate  department")
	@ApiResponse(responseCode = "202",description = "Creating new department successfull")
	@PutMapping("/creteorupdate")
	public ResponseEntity<?> createorupdate(@RequestBody @Valid DepartmentRequestDTO reqdto)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.createorupdate(reqdto));
	}
	
	
	@Operation(summary = "Get department details",description = "Get all department detailsD")
	@ApiResponse(responseCode = "200",description = "Fetching all department details successfull")
	@GetMapping("/view")
	public ResponseEntity<?> getAll()
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
	}
	
	
	@Operation(summary = "Find department by ID",description = "Get the department details based on departmen ID")
	@ApiResponse(responseCode = "200",description = "Fetching department details by department ID successfull")
	@GetMapping("/find/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
	}
	
	
	@Operation(summary = "Get all department details using pagination",description = "view all department details in page format")
	@ApiResponse(responseCode = "200",description = "Fetching all department details using pagination successfull")
	@GetMapping("/page/view")
	public ResponseEntity<?> getAll(@RequestParam int pageNo,@RequestParam int pageSize)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getAll(pageNo,pageSize));
	}
	
	
	@Operation(summary = "Delete department",description = "Delete department based on ID")
	@ApiResponse(responseCode = "200",description = "Deleting department successfull")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteById(id));
	}
	
}
