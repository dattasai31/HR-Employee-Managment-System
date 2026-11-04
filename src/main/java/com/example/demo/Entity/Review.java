package com.example.demo.Entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "Review.findAverageRatingOfAnEmployee",query = "select avg(r.rating) from Review r where r.employee.empId=?1")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
	@Column(nullable = false)
	private Double rating;
	private String remarks;
	@Column(nullable = false)
	private LocalDate reviewDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id")
	@JsonBackReference
	private Employee employee;

}
