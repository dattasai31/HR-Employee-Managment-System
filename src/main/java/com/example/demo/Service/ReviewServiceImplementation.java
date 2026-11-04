package com.example.demo.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.ReviewRequestDTO;
import com.example.demo.DTO.ReviewResponseDTO;
import com.example.demo.DTO.ReviewUpdateDTO;
import com.example.demo.Entity.Department;
import com.example.demo.Entity.Employee;
import com.example.demo.Entity.Review;
import com.example.demo.ExceptionHandling.Constants;
import com.example.demo.ExceptionHandling.ResourceNotFound;
import com.example.demo.Mapper.EmployeeMapper;
import com.example.demo.Mapper.ReviewMapper;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Repository.ReviewRepository;

import jakarta.persistence.Cacheable;

@Service
public class ReviewServiceImplementation implements ReviewService{
	@Autowired
	private ReviewRepository repository;
	@Autowired
	private EmployeeRepository emprepository;
	@Autowired
	private DepartmentRepository deptrepository;
	@Autowired
	private ReviewMapper mapper;
	
	private static final Logger logger=org.slf4j.LoggerFactory.getLogger(ReviewServiceImplementation.class);
	
	
	// method to add rating
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ReviewResponseDTO addRating(ReviewRequestDTO reqdto) 
	{
		logger.info("Adding rating started");
		Employee emp=emprepository.findById(reqdto.getEmpId()).orElseThrow(()-> new ResourceNotFound(Constants.Emp_Not_Present));
		Review rev=mapper.toEntity(reqdto);
		rev.setEmployee(emp);
		Review review=repository.save(rev);
		logger.info("Adding rating completed successfully");
		return mapper.toResponse(review);
	}
	
	
	// method to update rating
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CachePut(value = "review",key = "#reviewId")
	public ReviewResponseDTO updateRating(Long reviewId, ReviewUpdateDTO reqdto) 
	{
		logger.info("Updating rating of an employee started");
		Optional<Review> exist=repository.findById(reviewId);
		if(exist.isPresent())
		{
			Review rev=exist.get();
			mapper.updateEntity(reqdto, rev);
			Review review=repository.save(rev);
			logger.info("Updating rating of an employee completed successfully");
			return mapper.toResponse(review);
		}
		else
		{
			logger.error("Error occured while updating rating of an employee");
			throw new ResourceNotFound(Constants.Review_Not_Found);
		}
	}
	
	
	// method to get all ratings
	@Override
	@Transactional(readOnly = true)
	public List<ReviewResponseDTO> getAll() 
	{
		logger.info("Fetching all reviews started");
		List<Review> re=repository.findAll();
		logger.info("Fetching all reviews completed successfully");
		return mapper.toResponseList(re);
	}
	
	
	// method to delete a rating
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "review",key = "#reviewId")
	public String deleteById(Long reviewId) 
	{
		logger.info("Deleting a review by using review id started");
		Optional<Review> exist=repository.findById(reviewId);
		if(exist.isPresent())
		{
			Review review=exist.get();
			repository.deleteById(reviewId);
			logger.info("Deleting a review by using review id completed successfully");
			return "Review record deleted successfully";
		}
		else
		{
			logger.error("Error occured while deleting a review by using review id");
			throw new ResourceNotFound(Constants.Review_Not_Found);
		}
	}
	
	
	// method to get review by using review id
	@Override
	@Transactional(readOnly = true)
	@org.springframework.cache.annotation.Cacheable(value = "review",key = "#reviewId")
	public ReviewResponseDTO getById(Long reviewId) 
	{
		logger.info("Fetching review by review id started");
		Optional<Review> exist=repository.findById(reviewId);
		if(exist.isPresent())
		{
			Review review=exist.get();
			logger.info("Fetching review by review id completed successfully");
			return mapper.toResponse(review);
		}
		else
		{
			logger.error("Error occured while fetching review by review id");
			throw new ResourceNotFound(Constants.Review_Not_Found);
		}
	}
	
	
	// method to fetch all reviews of an employee
	@Override
	@Transactional(readOnly = true)
	public List<ReviewResponseDTO> getByEmpid(Long empId) 
	{
		logger.info("Fetching all reviews of an employee started");
		Employee emp=emprepository.findById(empId).orElseThrow(()-> new ResourceNotFound(Constants.Emp_Not_Present));
		List<Review> reviews=repository.findByEmployeeEmpId(empId);
		logger.info("Fetching all reviews of an employee completed successfully");
		return mapper.toResponseList(reviews);
	}
	
	
	// method to fetch all reviews on a particular date
	@Override
	@Transactional(readOnly = true)
	public List<ReviewResponseDTO> getByDate(LocalDate date) 
	{
		logger.info("Getting reviews on particular date started");
		List<Review> reviews=repository.findReviewByDate(date);
		logger.info("Getting reviews on particular date completed successfully");
		return mapper.toResponseList(reviews);
	}
	
	
	// method to get average rating of an employee
	@Override
	@Transactional(readOnly = true)
	public String averageRatingOfAnEmployee(Long empId) 
	{
		logger.info("Getting average rating of an employee started successfully");
		Employee emp=emprepository.findById(empId).orElseThrow(()->new ResourceNotFound(Constants.Emp_Not_Present));
		Double avg=repository.findAverageRatingOfAnEmployee(empId);
		logger.info("Getting average rating of an employee completed successfully");
		if(avg==null)
		{
			return "Average rating of employee with employeeid "+empId+" is: "+0.0;
		}
		else
		{
			return "Average rating of employee with employeeid "+empId+" is: "+avg;
		}
	}
	
	
	// method to get average rating of a department
	@Override
	@Transactional(readOnly = true)
	public String averageRatingDepartment(Long deptId) 
	{
		logger.info("Getting average rating pf department started successfully");
		Department dept=deptrepository.findById(deptId).orElseThrow(()->new ResourceNotFound(Constants.Dept_Not_Present));
		Double avg= repository.findAverageRatingByDept(deptId);
		logger.info("Getting average rating pf department completed successfully");
		if(avg==null)
		{
			return "Average rating of department with departmentid "+deptId+" is: "+0.0;
		}
		else
		{
			return "Average rating of department with departmentid "+deptId+" is: "+avg;
		}
	}

}
