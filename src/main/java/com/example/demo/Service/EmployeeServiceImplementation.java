package com.example.demo.Service;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.nio.file.StandardCopyOption;
import java.util.Set;

import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.EmployeeRequestDTO;
import com.example.demo.DTO.EmployeeResponseDTO;
import com.example.demo.Entity.Department;
import com.example.demo.Entity.Employee;
import com.example.demo.ExceptionHandling.Constants;
import com.example.demo.ExceptionHandling.ResourceNotFound;
import com.example.demo.Mapper.EmployeeMapper;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.EmployeeRepository;


@Service
public class EmployeeServiceImplementation implements EmployeeService{
	
	@Autowired
	private EmployeeRepository repository;
	@Autowired
	private DepartmentRepository deptrepository;	
	@Autowired
	private EmployeeMapper mapper;
	
	private static final String directory = "uploads/";
	
	private static final Logger logger=org.slf4j.LoggerFactory.getLogger(EmployeeServiceImplementation.class);
	
	
	// method for adding employee details
	@Override
	@Transactional(rollbackFor = Exception.class)
	public EmployeeResponseDTO addEmployee(EmployeeRequestDTO reqdto) 
	{
		logger.info("Adding new employee started");
		Employee e=mapper.toEntity(reqdto);
		Department department=deptrepository.findById(reqdto.getDeptId()).orElseThrow(()-> new ResourceNotFound(Constants.Dept_Not_Present));
		e.setDepartment(department);
		Employee saved=repository.save(e);
		logger.info("Adding new employee completed successfully");
		return mapper.toResponse(saved);
	}
	
	
	// method for updating employee details
	@Override
	@Transactional(rollbackFor = Exception.class)
	public EmployeeResponseDTO updateEmployee(Long empId,EmployeeRequestDTO reqdto) 
	{
		logger.info("Updating employee details started");
		Optional<Employee> exist=repository.findById(empId);
		if(exist.isPresent())
		{
			Employee e=exist.get();
			Department dep=deptrepository.findById(reqdto.getDeptId()).orElseThrow(()->new ResourceNotFound(Constants.Dept_Not_Present));
			e.setDepartment(dep);
			mapper.updateEntity(reqdto, e);
		
			Employee employee=repository.save(e);
			logger.info("Updating employee details completed successfully");
			return mapper.toResponse(employee);
		}
		else
		{
			logger.error("Error occured while updating employee details");
			throw new ResourceNotFound(Constants.Emp_Not_Present);
		}
	}

	
	// method for fetching all employee details
	@Override
	@Transactional(readOnly = true)
	public List<EmployeeResponseDTO> getAll() 
	{
		logger.info("Getting all employee details started");
		List<Employee> emp=repository.findAll();
		logger.info("Getting all employee details completed successfully");
		return mapper.toResponseList(emp);
	}

	
	// method for deleting employee details
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "employee",key = "#empId")
	public String deleteById(Long empId) 
	{
		logger.info("Deleting employee records by employee id started");
		Optional<Employee> exist=repository.findById(empId);
		if(exist.isPresent())
		{
			Employee emp=exist.get();
			repository.deleteById(empId);
			logger.info("Deleting employee records by employee id completed successfully");
			return "Employee deleted successfully";
		}
		else
		{
			logger.error("Error occured while deleting employee records by employee id");
			throw new ResourceNotFound(Constants.Emp_Not_Present);
		}
	}

	
	// method for getting employee details by employee id
	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "employee",key="#empId")
	public EmployeeResponseDTO getById(Long empId) 
	{
		logger.info("Fetching employee details by using employee id started");
		Optional<Employee> exist=repository.findById(empId);
		if(exist.isPresent())
		{
			Employee emp=exist.get();
			logger.info("Fetching employee details by using employee id completed successfully");
			return mapper.toResponse(emp);
		}
		else
		{
			logger.error("Error occured while fetching employee details by using employee id");
			throw new ResourceNotFound(Constants.Emp_Not_Present);
		}
	}

	
	// method for fetching all employee details using pagination
	@Override
	@Transactional(readOnly = true)
	public Page<EmployeeResponseDTO> getAll(int pageNo, int pageSize) 
	{
		logger.info("Displaying employee details page wise started");
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Employee> emp=repository.findAll(pageable);
		logger.info("Displayig employee details page wise completed successfully");
		return emp.map(mapper::toResponse);
	}

	
	// method for fetching employee details whose salary is greater than given salary
	@Override
	@Transactional(readOnly = true)
	public Page<EmployeeResponseDTO> getBySalaryGreaterThan(Double salary, int pageNo, int pageSize) 
	{
		logger.info("Finding employees greater then mentioned salary started");
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Employee> employee=repository.findBySalaryGreaterThan(salary,pageable);
		logger.info("Finding employees greater then mentioned salary completed successfully");
		return employee.map(mapper::toResponse);
	}

	
	// method for sorting employees according to their salary
	@Override
	public Page<EmployeeResponseDTO> sortBySalary(String field, int pageNo, int pageSize) 
	{
		logger.info("Grouping employees by salary started");
		Pageable pageable=PageRequest.of(pageNo, pageSize,Sort.by(field).descending());
		Page<Employee> emp=repository.findAll(pageable);
		logger.info("Grouping employees by salary completed");
		return emp.map(mapper::toResponse);
	}

	
	// method for getting employee details based on department
	@Override
	@Transactional(readOnly = true)
	public Page<EmployeeResponseDTO> getByDeptName(String deptName, int pageNo, int pageSize) 
	{
		logger.info("Fetching employee details based on department name started");
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Employee> emp=repository.getByDepartmentName(deptName, pageable);
		logger.info("Fetching employee details based on department name completed successfully");
		return emp.map(mapper::toResponse);
	}

	
	// method for fetching employees who are in this salary range
	@Override
	@Transactional(readOnly = true)
	public Page<EmployeeResponseDTO> getBySalaryRange(Double min, Double max, int pageNo, int pageSize) 
	{
		logger.info("Fetching employee details in between salary range started");
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Employee> emp=repository.findBySalaryBetween(min, max, pageable);
		logger.info("Fetching employee details in between salary range completed successfully");
		return emp.map(mapper::toResponse);
	}

	
	// method for uploading files
	@Override
	public String fileUpload(MultipartFile file)
	{
		try
		{
			Path path=Paths.get(directory);
			if(!(Files.exists(path)))
			{
				Files.createDirectories(path);
			}
			Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			return "File uploaded successfully: "+file.getOriginalFilename();
		}
		catch(IOException e)
		{
			throw new RuntimeException("Unable to upload file");
		}

	}

	
	// method for downloading files
	@Override
	public Resource download(String fileName) {
		try
		{
			Path filePath=Paths.get(directory).resolve(fileName).normalize();
			Resource resource=new UrlResource(filePath.toUri());
			if(!(resource.exists()))
			{
				throw new ResourceNotFound("File not found");
			}
			return resource;
		}
		catch(java.net.MalformedURLException e)
		{
			throw new RuntimeException("Error downloading file");
		}
	}

}
