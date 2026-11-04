package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.DepartmentRequestDTO;
import com.example.demo.DTO.DepartmentResponseDTO;
import com.example.demo.Entity.Department;
import com.example.demo.ExceptionHandling.Constants;
import com.example.demo.ExceptionHandling.ResourceNotFound;
import com.example.demo.Mapper.DepartmentMapper;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.EmployeeRepository;



@Service
public class DerpartmentServiceImplementation implements DepartmentService{
	
	@Autowired
	private  DepartmentRepository repository;
	@Autowired
	private EmployeeRepository emprepository;	
	@Autowired
	private DepartmentMapper mapper;
	
	private static final Logger logger=org.slf4j.LoggerFactory.getLogger(DerpartmentServiceImplementation.class);
	
	
	// method to create or update department details
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CachePut(value = "department",key="#result.deptId")
	public DepartmentResponseDTO createorupdate(DepartmentRequestDTO reqdto) 
	{
		Long id=reqdto.getDeptId();
		if(id != null)
		{
			logger.info("Updating department details started");
			Optional<Department> dept=repository.findById(id);
			if(dept.isPresent())
			{
				Department department=dept.get();
				mapper.updateEntity(reqdto, department);
	            Department saved = repository.save(department);
	            logger.info("Updating department details completed successfully");
	            return mapper.toResponse(saved);
			}
			else 
			{
				logger.error("Error occured while updating department details ");
				throw new ResourceNotFound(Constants.Dept_Not_Present);	
			}
		}
		else
		{
			logger.info("Creating new department started");
			Department department=mapper.toEntity(reqdto);
			Department dep=repository.save(department);
			logger.info("Creating new department completed succesfully");
			return mapper.toResponse(dep);
		}
	}

	
	// method to get all department details
	@Override
	@Transactional(readOnly = true)
	public List<DepartmentResponseDTO> getAll() 
	{
		logger.info("Fetching all department details started");
		List<Department> department=repository.findAll();
		logger.info("Fetching all department details completed successfully");
		return mapper.toResponseList(department);	
	}

	
	// method to delete department
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "department",key="#id")
	public String deleteById(Long id) 
	{
		logger.info("Deleting a department by ID started");
		Optional<Department> department=repository.findById(id);
		if(department.isPresent())
		{
			if(emprepository.existsByDepartmentDeptId(id))
			{
				throw new IllegalStateException("Cannot delete a department that still has employees");
			}
			repository.deleteById(id);
			logger.info("Deleting a department by id completed successfully");
			return "Department deleted successfully";
		}
		else
		{
			logger.error("Error occured while deleting a department by id");
			throw new ResourceNotFound(Constants.Dept_Not_Present);
		}
	}

	
	// method to get department details by using department id
	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "department",key="#id")
	public DepartmentResponseDTO getById(Long id) {
		
		Optional<Department> exist=repository.findById(id);
		logger.info("Fetching department details by department id started");
		if(exist.isPresent())
		{
			Department department=exist.get();
			logger.info("Fetching department details by department id completed successfully");
			return mapper.toResponse(department);
		}
		else
		{
			logger.error("Error occured while fetching department details by department id");
			throw new ResourceNotFound(Constants.Dept_Not_Present);
		}
	}


	// method to display all department details in page format
	@Override
	public Page<DepartmentResponseDTO> getAll(int pageNo, int pageSize) 
	{
		logger.info("Fetching all department details page wise started");
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Department> dept=repository.findAll(pageable);
		logger.info("Fetching all department details page wise completed successfully");
		return dept.map(mapper::toResponse);
	}
}
