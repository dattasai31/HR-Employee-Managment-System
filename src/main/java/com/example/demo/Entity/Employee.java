package com.example.demo.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long empId;
	
	@Column(nullable = false)
	private String empName;
	
	@Column(nullable = false,unique = true)
	private String mobileNumber;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String designation;
	
	@Column(nullable = false)
	private Double salary;
	
	@Column(nullable = false)
	private LocalDate joinDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="dept_id")
	@JsonBackReference
	private Department department;
	
	@OneToMany(mappedBy = "employee",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Leave> leaves=new ArrayList<>();
	
	@OneToMany(mappedBy = "employee",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Review> reviews=new ArrayList<>();
	
	@OneToMany(mappedBy = "employee",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Attendance> attendance=new ArrayList<>();
	

}
