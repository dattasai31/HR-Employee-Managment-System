package com.example.demo.Service;

import com.example.demo.DTO.ReviewRequestDTO;
import com.example.demo.DTO.ReviewResponseDTO;
import com.example.demo.DTO.ReviewUpdateDTO;

import java.time.LocalDate;
import java.util.*;

public interface ReviewService {
	ReviewResponseDTO addRating(ReviewRequestDTO reqdto);
	ReviewResponseDTO updateRating(Long reviewId,ReviewUpdateDTO reqdto);
	List<ReviewResponseDTO> getAll();
	String deleteById(Long reviewId);
	ReviewResponseDTO getById(Long reviewId);
	List<ReviewResponseDTO> getByEmpid(Long empId);
	List<ReviewResponseDTO> getByDate(LocalDate date);
	String averageRatingOfAnEmployee(Long empId);
	String averageRatingDepartment(Long deptId);

}
