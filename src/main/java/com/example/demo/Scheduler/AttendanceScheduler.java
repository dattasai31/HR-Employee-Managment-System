package com.example.demo.Scheduler;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.Service.AttendanceServiceImplementation;

@Component
public class AttendanceScheduler {
	@Autowired
	private AttendanceServiceImplementation service;
	

	@Scheduled(cron="#{'${attendance.report.cron}'}")
	public void absentEmployee()
	{
		System.out.println("Absent employees on this date: "+LocalDate.now());
		System.out.println("---------------------------");
		service.absentReport();
		System.out.println("----------------------------");
	}
	
	
	@Scheduled(cron="#{'${attendance.reset.cron}'}")
	public void reset()
	{
		System.out.println("Resetting the attendance status of all employees");
		service.reset();
		System.out.println("-----------------------------------");
	}
}
