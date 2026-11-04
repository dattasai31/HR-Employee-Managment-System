package com.example.demo.Entity;

import java.time.LocalDate;

import com.example.demo.Enum.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="leave_request")
public class Leave {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long leaveId;
	
	@Column(nullable = false)
	private String leaveType;
	
	@Column(nullable = false)
	private String reason;
	
	@Column(nullable = false)
	private LocalDate startDate;
	
	@Column(nullable = false)
	private LocalDate endDate;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status=Status.Applied;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id")
	@JsonBackReference
	private Employee employee;

}
