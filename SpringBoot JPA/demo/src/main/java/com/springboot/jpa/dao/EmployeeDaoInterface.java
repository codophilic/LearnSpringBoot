package com.springboot.jpa.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springboot.jpa.entities.Employee;

public interface EmployeeDaoInterface extends PagingAndSortingRepository<Employee, Integer>,CrudRepository<Employee,Integer>{

	
}
