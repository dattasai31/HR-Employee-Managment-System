# HR-Employee-Mangment-System
A Spring Boot REST API to manage employees, departments, attendance, leave and performance reviews.

## Tech Stack
Java, Spring Boot, Spring Data JPA, Jakarta Validation, Lombok, Maven, MySQL , Postman

## Modules
Department, Employee, Attendance, Leave, Performance Review

## Key Features
- CRUD for employees and departments (single method for department add and update)
- Daily attendance, one record per employee per day, with a scheduled job at 10:00 AM that automatically marks absent employees and generates an absentee list.
- Leave workflow: apply (starts as pending), then approve or reject
- Multiple performance reviews per employee, fetched by empId

## Concepts Used
OneToMany / ManyToOne, DTOs, validation, pagination, JPQL, global exception handling, scheduled tasks, AOP, Spring security, Transaction managment
