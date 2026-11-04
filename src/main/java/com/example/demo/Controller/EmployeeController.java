package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.EmployeeRequestDTO;
import com.example.demo.Service.EmployeeServiceImplementation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
@Validated
public class EmployeeController {
	@Autowired
	private EmployeeServiceImplementation service;
	
	
	@Operation(summary = "Add employee",description = "Add the employee details")
	@ApiResponse(responseCode = "202",description = "Adding employee successfull")
	@PostMapping("/add")
	public ResponseEntity<?> addEmployee(@RequestBody @Valid EmployeeRequestDTO reqdto)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(service.addEmployee(reqdto));
	}
	
	
	@Operation(summary = "Update employee details",description = "Update employee details by ID")
	@ApiResponse(responseCode = "200",description = "Updatin employee details successfull")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable Long id,@RequestBody @Valid EmployeeRequestDTO reqdto)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.updateEmployee(id, reqdto));
	}
	
	
	@Operation(summary = "View all employee details",description = "Get all employee details")
	@ApiResponse(responseCode = "200",description = "Fetching employee details successfull")
	@GetMapping("/view")
	public ResponseEntity<?> getAll()
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
	}
	
	
	@Operation(summary = "Delete employee record",description = "Deleting employee record bases on employee ID")
	@ApiResponse(responseCode = "200",description = "Deleting employee record successfull ")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteById(id));
	}
	
	
	@Operation(summary = "Find employee by ID",description = "Find employee by EmployeeID")
	@ApiResponse(responseCode = "200",description = "Finding employee by ID successfull")
	@GetMapping("/find/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
	}
	
	
	@Operation(summary ="View all employee details page wise",description = "Get all employee details page wise")
	@ApiResponse(responseCode = "200",description = "Fetching all employee details page wise successfull")
	@GetMapping("/page/all")
	public ResponseEntity<?> getAll(@RequestParam int pageNo,@RequestParam int pageSize)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getAll(pageNo, pageSize));
	}
	
	
	@Operation(summary = "Employees who has greater salary",description = "Get all employees whose salry is greater than given salary")
	@ApiResponse(responseCode = "200",description = "Fetching employees who has more salary than reference salary is successfull")
	@GetMapping("/salarygreater/{salary}")
	public ResponseEntity<?> getBySalaryGreaterThan(@PathVariable Double salary,@RequestParam int pageNo,@RequestParam int pageSize)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getBySalaryGreaterThan(salary, pageNo, pageSize));
		
	}
	
	
	@Operation(summary = "Sort by salary",description = "Sort all employees based on salary")
	@ApiResponse(responseCode = "200",description = "Sorting employees by salary completed successfully")
	@GetMapping("/page/salary")
	public ResponseEntity<?> sortBySalary(@RequestParam String field,@RequestParam int pageNo,@RequestParam int pageSize)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.sortBySalary(field, pageNo, pageSize));
	}
	
	
	@Operation(summary = "Group by department name",description = "Get all employees of same department")
	@ApiResponse(responseCode = "200",description = "Fetching employees of same department completed successfully")
	@GetMapping("/page/bydeptname/{name}")
	public ResponseEntity<?> getByDeptName(@PathVariable String name,@RequestParam int pageNo,@RequestParam int pageSize)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getByDeptName(name, pageNo, pageSize));
	}
	
	
	@Operation(summary = "Employees in salary range",description = "Get all employees in that salary range")
	@ApiResponse(responseCode = "200",description ="Fetching all employees in salary range completed successfully" )
	@GetMapping("/page/salrange/{min}/{max}")
	public ResponseEntity<?> getBySalaryRange(@PathVariable Double min,@PathVariable Double max,@RequestParam int pageNo,@RequestParam int pageSize)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.getBySalaryRange(min, max, pageNo, pageSize));
	}
	
	
	@Operation(summary = "Upload file",description = "Upload files")
	@ApiResponse(responseCode = "200",description = "File uploaded successfully")
	@PostMapping("/uploadfile")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file)
	{
		return ResponseEntity.status(HttpStatus.OK).body(service.fileUpload(file));
	}
	
	
	@Operation(summary = "Download file",description = "Download file")
	@ApiResponse(responseCode = "200",description = "File downloaded successfully")
	@GetMapping("/downloadfile/{filename}")
	public ResponseEntity<?> downloadFile( @PathVariable String filename)
	{
		Resource resource=service.download(filename);
		return ResponseEntity.ok().header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+resource.getFilename()+"\"").body(resource);
	}

}
