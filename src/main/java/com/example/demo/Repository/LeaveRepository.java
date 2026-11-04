package com.example.demo.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Leave;
import com.example.demo.Enum.Status;
@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long>{
	List<Leave> findByEmployeeEmpId(Long empId);
	
	List<Leave> findByStatus(Status status);
	
	
	

	
	
	
	

}
