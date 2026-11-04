package com.example.demo.Repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	Page<Employee> findAll(Pageable pageable);
	
	Page<Employee> findBySalaryGreaterThan(Double salary,Pageable pageable);
	
	@Query("select e from Employee e where e.department.deptName=?1")
	Page<Employee> getByDepartmentName(String deptName,Pageable pageable);
	
	Page<Employee> findBySalaryBetween(Double min,Double max,Pageable pageable);
	
	boolean existsByDepartmentDeptId(Long deptId);

}
