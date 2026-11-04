package com.example.demo.Service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.EmployeeRequestDTO;
import com.example.demo.DTO.EmployeeResponseDTO;
import com.example.demo.Entity.Employee;

public interface EmployeeService {
	EmployeeResponseDTO addEmployee(EmployeeRequestDTO reqdto);
	EmployeeResponseDTO updateEmployee(Long empId,EmployeeRequestDTO reqdto);
	List<EmployeeResponseDTO> getAll();
	EmployeeResponseDTO getById(Long empId);
	String deleteById(Long empId);
	Page<EmployeeResponseDTO> getAll(int pageNo,int pageSize);
	Page<EmployeeResponseDTO> getBySalaryGreaterThan(Double salary,int pageNo,int pageSize);
	Page<EmployeeResponseDTO> sortBySalary(String field,int pageNo,int pageSize);
	Page<EmployeeResponseDTO> getByDeptName(String deptName,int pageNo,int pageSize);
	Page<EmployeeResponseDTO> getBySalaryRange(Double min,Double max,int pageNo,int pageSize);
	String fileUpload(MultipartFile file);
	Resource download(String fileName);
	

}
