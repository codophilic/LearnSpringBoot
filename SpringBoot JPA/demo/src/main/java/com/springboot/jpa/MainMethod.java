package com.springboot.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.springboot.jpa.dao.CustomerDaoInterface;
import com.springboot.jpa.dao.EmployeeDaoInterface;
import com.springboot.jpa.entities.Customer;
import com.springboot.jpa.entities.Employee;

@SpringBootApplication
public class MainMethod {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(MainMethod.class, args);
		
		/**
		 * SpringBoot JPA automatically configures bean into IOC container
		 */
		CustomerDaoInterface cdi=context.getBean(CustomerDaoInterface.class);
		
		Customer c=new Customer();
		c.setName("Harsh");
		c.setCustAddress("Mumbai");
		
		/**
		 * Saving a single entity
		 */
		Customer cresult=cdi.save(c);
		System.out.println("Customer details saved: "+cresult.toString());

		Customer c1=new Customer();
		c1.setName("Meet");
		c1.setCustAddress("Delhi");
		
		Customer c2=new Customer();
		c2.setName("Jeet");
		c2.setCustAddress("MP");
		
		/**
		 * Saving multiple entities
		 */
		Iterable<Customer> allCustomers=cdi.saveAll(List.of(c1,c2));
		
		allCustomers.forEach(allcust->{
			System.out.println("Customer details saved: "+allcust.toString());
		});
		
		/**
		 * Fetch by ID
		 */
		Optional<Customer> opt=cdi.findById(3);
		System.out.println("Fetch Customer Details by ID: "+opt.get());
		
		// If ID not found then it returns null
		opt=cdi.findById(4);
		if(!opt.isPresent()) {
			System.out.println("Fetch Customer Details not found for ID 4");
		}
		
		/**
		 * Fetch all user
		 */
		allCustomers=cdi.findAll();
		System.out.println("All Customer Details");
		allCustomers.forEach(allcust->{
			System.out.println(allcust.toString());
		});
		
		/**
		 * Fetch by address
		 * Here springboot jpa provides a customized inbuild method
		 */
		List<Customer> allCustDetailsForMumbai=cdi.findByCustAddress("Mumbai");
		allCustDetailsForMumbai.forEach(i->{
			System.out.println(i.toString());
		});
		
		/**
		 * Update Customer details using Id
		 */
		opt=cdi.findById(3);
		Customer c3=opt.get();
		c3.setName("Donga");
		cdi.save(c3);
		System.out.println("Updated user details: "+cdi.findById(3).get());
		
		/**
		 * Delete user by ID
		 */
		cdi.deleteById(3);
		System.out.println("Deleted user of ID 3:"+cdi.findById(3).isPresent());
		
		/**
		 * Delete All Users
		 */
		cdi.deleteAll();
		
		/**
		 * Custom Query
		 */	
		Customer cust1=new Customer();
		cust1.setName("Harsh Pandya");
		cust1.setCustAddress("Mumbai");
		Customer cust2=new Customer();
		cust2.setName("Meet Pandya");
		cust2.setCustAddress("Mumbai");
		Customer cust3=new Customer();
		cust3.setName("Harsh Donga");
		cust3.setCustAddress("Mumbai");
		
		cdi.saveAll(List.of(cust1,cust2,cust3));

		/**
		 * Derived Query
		 */
		List<Customer> customerfound=cdi.findByNameAndCustAddress("Harsh Pandya", "Mumbai");
		System.out.println("Finding details using method findByNameAndCustAddress()");
		customerfound.forEach(i->{
			System.out.println(i);
		});
		
		/**
		 * JPQL
		 */
		customerfound=cdi.fetchUsingLowerCaseNameAndAddress("Harsh Pandya".toUpperCase(), "Mumbai".toUpperCase());
		System.out.println("Finding details using method fetchUsingLowerCaseNameAndAddress()");
		customerfound.forEach(i->{
			System.out.println(i);
		});

		/**
		 * Native SQL
		 */
		Customer thirdCustomer=cdi.retrieveThirdCustomerOnly();
		System.out.println("Third Customer Details: "+thirdCustomer.toString());
		
		
		EmployeeDaoInterface edi=context.getBean(EmployeeDaoInterface.class);
		
		/**
		 * Creating Employee details
		 */
		Employee emp1=new Employee();
		emp1.setName("Harsh");
		emp1.setSalary(1200000);
		
		Employee emp2=new Employee();
		emp2.setName("Meet");
		emp2.setSalary(2400000);
		
		/**
		 * Saving data using CrudRepository
		 */
		edi.saveAll(List.of(emp1,emp2));
		
		/**
		 * Sort data by id
		 */
		Sort sort = Sort.by("id").descending();
		Iterable<Employee> sortedEmployesbyId = edi.findAll(sort);		
		sortedEmployesbyId.forEach(i->{
			System.out.println(i.toString());
		});
		
		/**
		 * Creating 100 Records
		 */
		for(int i=0;i<101;i++) {
			Employee emp=new Employee();
			emp.setName("User"+i);
			emp.setSalary(i);
			edi.save(emp);
		}
		
		/**
		 * Implementing Pagination for 5 records skipping first 10
		 * 
		 * Set page number 2 (which consist set of 5 records starting from 11-15)
		 */
		Pageable pageable = PageRequest.of(2,5);
		Page<Employee> employeesPage = edi.findAll(pageable);

		List<Employee> employees = employeesPage.getContent();
		System.out.println("Pagination");
		employees.forEach(i->{
			System.out.println(i.toString());
		});
	}

}
