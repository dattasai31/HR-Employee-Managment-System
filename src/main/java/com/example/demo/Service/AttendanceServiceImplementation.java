package com.example.demo.Service;

import java.beans.Transient;
import java.lang.StackWalker.Option;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.AttendanceRequestDTO;
import com.example.demo.DTO.AttendanceResponseDTO;
import com.example.demo.Entity.Attendance;
import com.example.demo.Entity.Employee;
import com.example.demo.ExceptionHandling.Constants;
import com.example.demo.ExceptionHandling.ResourceNotFound;
import com.example.demo.Mapper.AttendanceMapper;
import com.example.demo.Repository.AttendanceRepository;
import com.example.demo.Repository.EmployeeRepository;

@Service
public class AttendanceServiceImplementation implements AttendanceService {
	@Autowired
	private AttendanceRepository repository;
	@Autowired
	private EmployeeRepository emprepository;
	@Autowired
	private AttendanceMapper mapper;
	
	private static final Logger logger=org.slf4j.LoggerFactory.getLogger(AttendanceServiceImplementation.class);
	
	
	// method to checkin
	@Override
	@Transactional(rollbackFor = Exception.class)
	public AttendanceResponseDTO logIn(Long empId, AttendanceRequestDTO reqdto) 
	{
		logger.info("Log in started");
		Employee emp=emprepository.findById(empId).orElseThrow(()->new ResourceNotFound(Constants.Emp_Not_Present));
		LocalDate today=LocalDate.now();
		Optional<Attendance> existing=repository.findByEmployeeEmpIdAndCheckinDate(empId, today);
		Attendance att;
		if(existing.isPresent())
		{
			att=existing.get();
		}
		else
		{
			att=new Attendance();
			att.setEmployee(emp);
			att.setCheckinDate(today);	
		}	
		att.setCheckIn(reqdto.getCheckIn());
		att.setAttendanceStatus("Present");
		Attendance attendance=repository.save(att);
		logger.info("Log in completed successfully");
		return mapper.toResponse(attendance);
	}
	
	
	// method to get all attendance records
	@Override
	@Transactional(readOnly = true)
	public List<AttendanceResponseDTO> getAll() 
	{
		logger.info("Fetching all attendance records started");
		List<Attendance> attendance=repository.findAll();
		logger.info("Fetching all attendance records completed successfully");
		return mapper.toResponseList(attendance);
	}
	
	
	// method to delete an attendance record
	@Override
	@CacheEvict(value = "attendance",key = "#attendanceId")
	public String deleteById(Long attendanceId) 
	{
		logger.info("Deleting attendance record by attendance id started");
		Optional<Attendance> exist=repository.findById(attendanceId);
		if(exist.isPresent())
		{
			Attendance attendance=exist.get();
			repository.deleteById(attendanceId);
			logger.info("Deleting attendance record by attendance id completed successfully");
			return "Attendance record deleted successfully";
		}
		else
		{
			logger.error("Error occured while deleting attendance record by attendance id");
			throw new ResourceNotFound(Constants.Attendance_Not_Found);
			
		}
	}
	
	
	// method to get attendance record using attendance id
	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "attendance",key = "#attendanceId")
	public AttendanceResponseDTO getById(Long attendanceId) 
	{
		logger.info("Fetching attendance record by attendance id started");
		Optional<Attendance> exist=repository.findById(attendanceId);
		if(exist.isPresent())
		{
			Attendance attendance=exist.get();
			logger.info("Fetching attendance record by attendance id completed successfully");
			return mapper.toResponse(attendance);
		}
		else
		{
			logger.error("Error occured while fetching attendance record by attendance id");
			throw new ResourceNotFound(Constants.Attendance_Not_Found);
		}
	}
	
	
	// method to get all attendance records of an employee
	@Override
	@Transactional(readOnly = true)
	public List<AttendanceResponseDTO> getByEmpId(Long empId) 
	{
		logger.info("Fetching attendance records of an employee started ");
		Employee emp=emprepository.findById(empId).orElseThrow(()-> new ResourceNotFound(Constants.Emp_Not_Present));
		List<Attendance> attendance=repository.findByEmployeeEmpId(empId);
		logger.info("Fetching attendance records of an employee completed successfully");
		return mapper.toResponseList(attendance);	
	}
	
	
	// method for generating absent report(used in scheduler)
	@Override
	@Transactional(readOnly = true)
	public void absentReport() 
	{
		logger.info("Generating absent employee list started");
		List<Attendance> att=repository.findByCheckinDate(LocalDate.now());
		for(Attendance attendance:att)
		{
			if(!(attendance.getCheckIn()))
			{
				System.out.println("--------------------------------");
				System.out.println(attendance.getEmployee().getEmpName()+" marked absent for "+LocalDate.now());
				System.out.println("---------------------------------");
			}
		}
		logger.info("Generating absent employees list completed successfully");
		
	}
	
	
	// method to reset the checkin and attendance status so that employees can mark attendance on the next day(used in scheduler)
	@Override
	public void reset() 
	{
		logger.info("Resetting attendance status of employees started");
		LocalDate today=LocalDate.now();
		for(Employee emp:emprepository.findAll(Sort.by("empId")))
		{
			if(!(repository.existsByEmployeeEmpIdAndCheckinDate(emp.getEmpId(), today)))
			{
				Attendance a=new Attendance();
				a.setEmployee(emp);
				a.setCheckinDate(today);
				a.setCheckIn(false);
				a.setAttendanceStatus("Absent");
				repository.save(a);
			}
		}
		logger.info("Resetting attendance status of employees completed successfully");
		
	}

	
	// method to get absent employees on particular date
	@Override
	@Transactional(readOnly = true)
	public List<AttendanceResponseDTO> getAbsenteesByDate(LocalDate date) 
	{
		logger.info("Fetching absent employees on a particular date started successfully");
		List<Attendance> attendance=repository.findByCheckinDate(date);
		List<Attendance> att=new ArrayList<>();
		for(Attendance atten:attendance)
		{
			if(atten.getAttendanceStatus().equalsIgnoreCase("Absent"))
			{
				att.add(atten);
			}
		}
		logger.info("Fetching absent employees on particular date completed successfully");
		return mapper.toResponseList(att);
	}


	// method to get how many days employee present between two dates
	@Override
	@Transactional(readOnly = true)
	public Long presentDays(Long empId,LocalDate fromDate,LocalDate toDate) 
	{
		logger.info("Fetching total present days of an employee started successfully");
		Employee emp=emprepository.findById(empId).orElseThrow(()-> new ResourceNotFound(Constants.Emp_Not_Present));
		if(toDate.isBefore(fromDate))
		{
			logger.error("Error occured while fetching total present days of an employee");
			throw new IllegalArgumentException("To date cannot be before from date");
		}
		logger.info("Fetching total present days of an employee completed successfully");
		return repository.presentDays(empId, fromDate, toDate);
		
	}


	// method to get attendance percentage of an employee between two dates
	@Override
	@Transactional(readOnly = true)
	public Double attendancePercentage(Long empId, LocalDate fromDate, LocalDate toDate) 
	{
		logger.info("Fetching attendance percentage of an employee started");
		Employee emp=emprepository.findById(empId).orElseThrow(()-> new ResourceNotFound(Constants.Emp_Not_Present));
		if(toDate.isBefore(fromDate))
		{
			logger.error("Error occured while fetching attendance percentage of an employee");
			throw new IllegalArgumentException("To date cannot be before from date");
		}
		Long presentDays=repository.presentDays(empId, fromDate, toDate);
		Long totalDays=ChronoUnit.DAYS.between(fromDate, toDate)+1;
		double percentage=(presentDays*100.0)/totalDays;
		logger.info("Fetching attendance percentage of an employee completed successfully");
		return Math.round(percentage*100.0)/100.0;
	}

}
