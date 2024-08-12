package com.springboot.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.springboot.rest.entities.Employee;
import com.springboot.rest.service.EmployeeService;

//@Controller
@RestController
@RequestMapping("/apicontroller")
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
	@PostMapping( path="/employee", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE },  produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Employee createEmployeeDetails(@RequestBody Employee emp) {
		return employeeService.createEmployeeDetails(emp);
	}
	
	/**
	 * Creating a Multiple Employee
	 */
	@PostMapping( path="/multipleEmployee", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE },  produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<Employee> createMultipleEmployeeDetails(@RequestBody List<Employee> emp) {

		return employeeService.createMultipleEmployeeDetails(emp);
	}
	
	/**
	 * Updating existing employee using emp_id
	 */
	@PutMapping("/update/{empid}")
	public ResponseEntity<Employee> updateEmployeeDetails(@PathVariable int empid, @RequestBody Employee updateEmp) {
		Employee updatedEmp=employeeService.updateEmployeeDetails(empid,updateEmp);
		if(updatedEmp==null) {
			/**
			 * Setting the Status and passing the null value
			 */
			updatedEmp= new Employee();
			updatedEmp.setName("Not Found");
			updatedEmp.setDept("Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedEmp);
		}
		return ResponseEntity.ok().body(updatedEmp);
	}
	
	/**
	 * Delete single employee using emp_id
	 */
	@DeleteMapping("/delete/{empid}")
	public void deleteExistingEmployee(@PathVariable int empid) {
		employeeService.deleteEmployeeDetails(empid);
	}
}

