package com.example.demo.Service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.DTO.AttendanceRequestDTO;
import com.example.demo.DTO.AttendanceResponseDTO;

public interface AttendanceService {
	AttendanceResponseDTO logIn(Long empId, AttendanceRequestDTO reqdto);
	List<AttendanceResponseDTO> getAll();
	String deleteById(Long attendanceId);
	AttendanceResponseDTO getById(Long attendanceId);
	List<AttendanceResponseDTO> getByEmpId(Long empId);
	List<AttendanceResponseDTO> getAbsenteesByDate(LocalDate date);
	void absentReport();
	void reset();
	Long presentDays(Long empId,LocalDate fromDate,LocalDate toDate);
	Double attendancePercentage(Long empId,LocalDate fromDate,LocalDate toDate);

}
