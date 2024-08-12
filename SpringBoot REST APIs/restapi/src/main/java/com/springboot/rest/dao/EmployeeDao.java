package com.springboot.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rest.entities.Employee;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {

	Employee findByEmpid(int empid);
	
	void deleteByEmpid(int empid);
}
