package com.springboot.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.security.entities.Customer;
import com.springboot.security.service.CustomerService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerService customerService;

	@PostMapping("/customer-registration")
	public ResponseEntity<String> createCustomer(@RequestBody Customer customer){
		try {
			System.out.println(customer.toString());
			Customer cust=customerService.saveCustomerDetails(customer);
			return ResponseEntity.ok().body("Customer details created");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.internalServerError().body("Issue occured while creating customer details");
	}
}
