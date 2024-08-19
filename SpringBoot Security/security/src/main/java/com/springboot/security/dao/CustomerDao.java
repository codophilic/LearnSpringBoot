package com.springboot.security.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.security.entities.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer>{
	
	Customer findByEmailid(String emailid);

}
