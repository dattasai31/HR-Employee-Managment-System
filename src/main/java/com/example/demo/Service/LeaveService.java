package com.example.demo.Service;
import java.time.LocalDate;
import java.util.*;
import com.example.demo.DTO.LeaveApprovalDTO;
import com.example.demo.DTO.LeaveRequestDTO;
import com.example.demo.DTO.LeaveResponseDTO;
import com.example.demo.Enum.Status;

public interface LeaveService {
	LeaveResponseDTO applyLeave(LeaveRequestDTO reqdto);
	LeaveResponseDTO updateLeave(Long leaveId, LeaveApprovalDTO apdto);
	List<LeaveResponseDTO> getAll();
	String deleteLeaveById(Long leaveId);
	LeaveResponseDTO getById(Long leaveId);
	List<LeaveResponseDTO> getByEmpid(Long empId);
	List<LeaveResponseDTO> appliedLeaves(Status status);
	
	
}
