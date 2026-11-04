package com.example.demo.Service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.DTO.DepartmentRequestDTO;
import com.example.demo.DTO.DepartmentResponseDTO;
import com.example.demo.Entity.Department;

public interface DepartmentService {
	DepartmentResponseDTO createorupdate(DepartmentRequestDTO reqdto);
	List<DepartmentResponseDTO> getAll();
	String deleteById(Long id);
	DepartmentResponseDTO getById(Long id);
	Page<DepartmentResponseDTO> getAll(int pageNo,int pageSize);

}
