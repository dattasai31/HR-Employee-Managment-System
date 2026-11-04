package com.example.demo.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Review;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	List<Review> findByEmployeeEmpId(Long empId);
	
	@Query(value = "select * from review where review_date=?1",nativeQuery = true)
	List<Review> findReviewByDate(LocalDate date);
	
	Double findAverageRatingOfAnEmployee(Long empId);
	
	@Query(value = "select avg(r.rating) from review r join employee e on r.employee_id=e.emp_id where e.dept_id=?1",nativeQuery = true)
	Double findAverageRatingByDept(Long deptId);

}
