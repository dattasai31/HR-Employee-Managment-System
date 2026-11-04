package com.example.demo.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Attendance;
import com.example.demo.Entity.Review;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	List<Attendance> findByEmployeeEmpId(Long empId);
	
	boolean existsByEmployeeEmpIdAndCheckinDate(Long empId, LocalDate date);
	 
	Optional<Attendance> findByEmployeeEmpIdAndCheckinDate(Long empId, LocalDate date);
	
	@Query("select a from Attendance a where a.checkinDate=?1")
	List<Attendance> findByCheckinDate(LocalDate date);
	
	@Query("select count(a) from Attendance a where a.employee.empId= ?1 and (a.checkIn=true) and (a.checkinDate between ?2 and ?3)")
	Long presentDays(Long empId,LocalDate fromDate,LocalDate toDate);

}
