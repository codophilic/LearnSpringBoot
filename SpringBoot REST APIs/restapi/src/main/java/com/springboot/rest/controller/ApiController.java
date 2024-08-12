package com.springboot.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.entities.Employee;
import com.springboot.rest.service.EmployeeService;

//@Controller
@RestController
@RequestMapping("/employee")
public class ApiController {

	/*
	 * @RequestMapping(path="/sample",method = RequestMethod.GET)
	 * 
	 * @ResponseBody public String sampleMethod() { return
	 * "This is a sample response"; }
	 */
	
	@GetMapping("/sample")
	public String sampleMethod() {
		return "This is a sample response";
	}
	
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * Fetch All Employees
	 */
	@GetMapping("/allEmployees")
	public List<Employee> fetchAllEmployees(){
		return employeeService.fetchAllEmployees();
	}
	
	/**
	 * Fetch Employee using emp_id
	 */
	@GetMapping("/employee/{empid}")
	public Employee fetchEmployeeUsingEmpid(@PathVariable("empid") int empid) {
		return employeeService.fetchEmployeeUsingEmpid(empid);
	}
	
	/**
	 * Creating a new Employee
	 */
	@PostMapping("/employee")
	public Employee createEmployeeDetails(@RequestBody Employee emp) {
		return employeeService.createEmployeeDetails(emp);
	}
	
	/**
	 * Updating existing employee using emp_id
	 */
	@PutMapping()
	public Employee updateEmployeeDetails(Employee emp) {
		return employeeService.updateEmployeeDetails(emp);
	}
}
