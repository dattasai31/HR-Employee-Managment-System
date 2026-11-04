package com.example.demo.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.DTO.EmployeeRequestDTO;
import com.example.demo.DTO.EmployeeResponseDTO;
import com.example.demo.Entity.Employee;

@Mapper(componentModel = "spring", uses = {LeaveMapper.class,ReviewMapper.class,AttendanceMapper.class})
public interface EmployeeMapper {

	@Mapping(target = "empId", ignore = true)
	@Mapping(target = "department", ignore = true)
	@Mapping(target = "leaves", ignore = true)
	@Mapping(target = "reviews", ignore = true)
	@Mapping(target = "attendance", ignore = true)
	Employee toEntity(EmployeeRequestDTO requestdto);

	@Mapping(source = "department.deptId", target = "deptId")
	@Mapping(source = "department.deptName", target = "deptName")
	@Mapping(source = "reviews", target = "review")
	EmployeeResponseDTO toResponse(Employee emp);

	List<EmployeeResponseDTO> toResponseList(List<Employee> e);

	@Mapping(target = "empId", ignore = true)
	@Mapping(target = "department", ignore = true)
	@Mapping(target = "leaves", ignore = true)
	@Mapping(target = "reviews", ignore = true)
	@Mapping(target = "attendance", ignore = true)
	void updateEntity(EmployeeRequestDTO reqdto, @MappingTarget Employee e);
}