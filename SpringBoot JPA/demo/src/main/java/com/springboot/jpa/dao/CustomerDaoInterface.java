package com.springboot.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.springboot.jpa.entities.Customer;

public interface CustomerDaoInterface extends CrudRepository<Customer, Integer>{

	List<Customer> findByCustAddress(String custAddress);
	
	/**
	 * Find data using name as well as customer address
	 */
	List<Customer> findByNameAndCustAddress(String name, String custAddress);
	
	/**
	 * JPQL (Java Persistence Query Language, ORM based queries)
	 */
	@Query("SELECT e FROM Customer e WHERE lower(e.name) = lower(:customer_name) AND lower(e.custAddress) = lower(:customer_address)")
	List<Customer> fetchUsingLowerCaseNameAndAddress(@Param("customer_name") String firstName, @Param("customer_address") String address);

	/**
	 * Native SQL Query or original way to write SQL query
	 */
	@Query(value = "SELECT * from Customer order by cust_id limit 2,1",nativeQuery = true)
	Customer retrieveThirdCustomerOnly();
}
