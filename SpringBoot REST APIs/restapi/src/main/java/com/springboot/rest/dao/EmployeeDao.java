package com.springboot.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.rest.entities.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Integer> {

}
