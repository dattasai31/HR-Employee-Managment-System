package com.example.demo.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.DTO.DepartmentRequestDTO;
import com.example.demo.DTO.DepartmentResponseDTO;
import com.example.demo.DTO.EmployeeRequestDTO;
import com.example.demo.DTO.EmployeeResponseDTO;
import com.example.demo.Entity.Department;
import com.example.demo.Entity.Employee;

@Mapper(componentModel = "spring",uses = EmployeeMapper.class)
public interface DepartmentMapper {
	@Mapping(target = "deptId",ignore = true)
	@Mapping(target = "employees",ignore = true)
	Department toEntity(DepartmentRequestDTO requestdto);
	
	DepartmentResponseDTO toResponse(Department dep);
	
	List<DepartmentResponseDTO> toResponseList(List<Department> d);
	
	@Mapping(target = "deptId",ignore = true)
	@Mapping(target = "employees",ignore = true)
	void updateEntity(DepartmentRequestDTO reqdto,@MappingTarget Department d);

	
}
