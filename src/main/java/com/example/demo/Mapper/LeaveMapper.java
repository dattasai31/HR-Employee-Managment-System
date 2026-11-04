
package com.example.demo.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.DTO.EmployeeRequestDTO;
import com.example.demo.DTO.EmployeeResponseDTO;
import com.example.demo.DTO.LeaveApprovalDTO;
import com.example.demo.DTO.LeaveRequestDTO;
import com.example.demo.DTO.LeaveResponseDTO;
import com.example.demo.Entity.Employee;
import com.example.demo.Entity.Leave;

@Mapper(componentModel = "spring")
public interface LeaveMapper {
	@Mapping(target = "leaveId",ignore = true)
	@Mapping(target = "employee",ignore = true)
	@Mapping(target = "status",ignore=true)
	Leave toEntity(LeaveRequestDTO requestdto);
	
	@Mapping(source = "employee.empId", target = "empId")
	LeaveResponseDTO toResponse(Leave leave);

	List<LeaveResponseDTO> toResponseList(List<Leave> le);
	
	@Mapping(target = "leaveId",ignore = true)
	@Mapping(target = "employee",ignore = true)
	void updateEntity(LeaveApprovalDTO ledto,@MappingTarget Leave leave);

}
