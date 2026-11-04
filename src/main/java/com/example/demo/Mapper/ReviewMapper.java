package com.example.demo.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.DTO.EmployeeRequestDTO;
import com.example.demo.DTO.LeaveRequestDTO;
import com.example.demo.DTO.LeaveResponseDTO;
import com.example.demo.DTO.ReviewRequestDTO;
import com.example.demo.DTO.ReviewResponseDTO;
import com.example.demo.DTO.ReviewUpdateDTO;
import com.example.demo.Entity.Employee;
import com.example.demo.Entity.Leave;
import com.example.demo.Entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
	@Mapping(target = "reviewId",ignore = true)
	@Mapping(target = "employee",ignore = true)
	Review toEntity(ReviewRequestDTO requestdto);
	
	@Mapping(source = "employee.empId",target = "empId")
	ReviewResponseDTO toResponse(Review review);
	
	List<ReviewResponseDTO> toResponseList(List<Review> review);
	
	@Mapping(target = "reviewId",ignore = true)
	@Mapping(target = "employee",ignore = true)
	void updateEntity(ReviewUpdateDTO redto,@MappingTarget Review review);

}
