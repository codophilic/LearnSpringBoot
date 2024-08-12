package com.springboot.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.dao.EmployeeDao;
import com.springboot.rest.entities.Employee;

@Service
@Transactional
public class EmployeeService {
	
	@Autowired
	private EmployeeDao employeeDao;

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	/**
	 * Fetch All Employees
	 */
	public List<Employee> fetchAllEmployees(){
		return employeeDao.findAll();
	}
	
	/**
	 * Fetch Employee using emp_id
	 */
	public Employee fetchEmployeeUsingEmpid(int empid) {
		return employeeDao.findByEmpid(empid);
	}
	
	/**
	 * Creating a new Employee
	 */
	public Employee createEmployeeDetails(Employee emp) {
		return employeeDao.save(emp);
	}

	/**
	 * Save Multiple Employees
	 */
	public List<Employee> createMultipleEmployeeDetails(List<Employee> emp) {
		return employeeDao.saveAll(emp);
	}
	
	/**
	 * Updating existing employee using emp_id
	 */
	public Employee updateEmployeeDetails(int id, Employee updatedEmp) {
		System.out.println(id);
		Employee fetchEmpDetails=employeeDao.findByEmpid(id);
		if(fetchEmpDetails==null) {
			return null;
		}
		System.out.println(fetchEmpDetails);
		fetchEmpDetails.setName(updatedEmp.getName());
		fetchEmpDetails.setDept(updatedEmp.getDept());
		return employeeDao.save(fetchEmpDetails);
	}
	
	/**
	 * Delete single employee using emp_id
	 */
	public void deleteEmployeeDetails(int empid) {
		employeeDao.deleteByEmpid(empid);
	}
}
