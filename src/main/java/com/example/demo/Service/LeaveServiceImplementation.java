package com.example.demo.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.LeaveApprovalDTO;
import com.example.demo.DTO.LeaveRequestDTO;
import com.example.demo.DTO.LeaveResponseDTO;
import com.example.demo.Entity.Employee;
import com.example.demo.Entity.Leave;
import com.example.demo.Enum.Status;
import com.example.demo.ExceptionHandling.Constants;
import com.example.demo.ExceptionHandling.ResourceNotFound;
import com.example.demo.Mapper.LeaveMapper;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Repository.LeaveRepository;


@Service
public class LeaveServiceImplementation implements LeaveService{
	@Autowired
	private LeaveRepository repository;	
	@Autowired
	private EmployeeRepository emprepository;	
	@Autowired
	private LeaveMapper mapper;
	
	private static final Logger logger=org.slf4j.LoggerFactory.getLogger(LeaveServiceImplementation.class);
	

	// method for applying leave
	@Override
	@Transactional(rollbackFor = Exception.class)
	public LeaveResponseDTO applyLeave(LeaveRequestDTO reqdto) 
	{
		logger.info("Applying leave started");
		Employee emp=emprepository.findById(reqdto.getEmpId()).orElseThrow(( )-> new ResourceNotFound(Constants.Emp_Not_Present));
		if(reqdto.getEndDate().isBefore(reqdto.getStartDate()))
		{
			logger.error("Error occured while applying leave");
			throw new IllegalArgumentException("End date cannot be before start date");
		}
		Leave lea=mapper.toEntity(reqdto);
		lea.setEmployee(emp);
		Leave leave=repository.save(lea);
		logger.info("Leave applied successfully");
		return mapper.toResponse(leave);
	}

	
	// method for approving/rejecting leave
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CachePut(value = "leave",key = "#leaveId")
	public LeaveResponseDTO updateLeave(Long leaveId, LeaveApprovalDTO apdto) 
	{
		logger.info("Updating leave details started");
		Optional<Leave> exist=repository.findById(leaveId);
		if(exist.isPresent())
		{
			Leave lea=exist.get();
			mapper.updateEntity(apdto, lea);
			Leave leave=repository.save(lea);
			logger.info("Updating leave details completed successfully");
			return mapper.toResponse(leave);
		}
		else
		{
			logger.error("Error occured while updating leave details");
			throw new ResourceNotFound(Constants.Leave_Not_Found);
		}
	}

	
	// method to get all leave details
	@Override
	@Transactional(readOnly = true)
	public List<LeaveResponseDTO> getAll() 
	{
		logger.info("Fetching all leave details started");
		List<Leave> leave=repository.findAll();
		logger.info("Fetching all leave details completed successfully");
		return mapper.toResponseList(leave);	
	}

	
	// method to delete leave
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "leave",key = "#leaveId")
	public String deleteLeaveById(Long leaveId) 
	{
		logger.info("Deleting leave record by leave id started");
		Optional<Leave> exist=repository.findById(leaveId);
		if(exist.isPresent())
		{
			Leave lea=exist.get();
			repository.deleteById(leaveId);
			logger.info("Deleting leave record by leave id completed successfully");
			return "Leave record deleted successfully";
		}
		else
		{
			logger.error("Error occured while deleting leave record by leave id");
			throw new ResourceNotFound(Constants.Leave_Not_Found);
		}
	}

	
	// method to get leave details by leave id
	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "leave",key = "#leaveId")
	public LeaveResponseDTO getById(Long leaveId) 
	{
		logger.info("Getting leave details by leave id started");
		Optional<Leave> exist=repository.findById(leaveId);
		if(exist.isPresent())
		{
			Leave leave=exist.get();
			logger.info("Getting leave details by leave id completed successfully");
			return mapper.toResponse(leave);
		}
		else
		{
			logger.error("Error occured while getting leave details by leave id");
			throw new ResourceNotFound(Constants.Leave_Not_Found);
		}
	}

	
	// method to get all leaves applied by an employee
	@Override
	@Transactional(readOnly = true)
	public List<LeaveResponseDTO> getByEmpid(Long empId) 
	{
		Employee emp=emprepository.findById(empId).orElseThrow(()->new ResourceNotFound(Constants.Emp_Not_Present));
		logger.info("Getting leave details of an employee by employee id started");
		List<Leave> leave=repository.findByEmployeeEmpId(empId);
		logger.info("Getting leave details of an employee by employee id completed successfully");
		return mapper.toResponseList(leave);
	}

	
	// method to get all pending leaves
	@Override
	@Transactional(readOnly = true)
	public List<LeaveResponseDTO> appliedLeaves(Status status) 
	{
		logger.info("Fetching pending leaves started");
		List<Leave> leave=repository.findByStatus(status);
		logger.info("Fetching pending leaves completed successfully");
		return mapper.toResponseList(leave);
	}

}
