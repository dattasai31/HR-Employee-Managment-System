package com.example.demo.Repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	boolean existsByDeptName(String deptName);
	
	Page<Department> findAll(Pageable pageable);

}
