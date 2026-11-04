package com.example.demo.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.DTO.AttendanceRequestDTO;
import com.example.demo.DTO.AttendanceResponseDTO;
import com.example.demo.DTO.LeaveRequestDTO;
import com.example.demo.Entity.Attendance;
import com.example.demo.Entity.Leave;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {
	@Mapping(target = "attendanceId",ignore = true)
	@Mapping(target = "employee",ignore = true)
	@Mapping(target = "checkinDate",ignore = true)
	@Mapping(target = "attendanceStatus",ignore = true)
	Attendance toEntity(AttendanceRequestDTO requestdto);
	
	@Mapping(source="employee.empId",target = "empId")
	@Mapping(source="employee.empName",target = "empName")
	AttendanceResponseDTO toResponse(Attendance attendance);
	
	List<AttendanceResponseDTO> toResponseList(List<Attendance> attendance);
	

}
