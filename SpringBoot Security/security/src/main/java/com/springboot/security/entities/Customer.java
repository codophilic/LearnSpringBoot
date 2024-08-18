package com.springboot.security.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "customer_table")
@Getter
@Setter
@ToString
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "customer_emailid", unique = true)
	private String emailid;
	
	@Column(name = "customer_password")
	private String pwd;
	
	@Column(name = "customer_active")
	private String isActive;
	
	@Column(name = "customer_role")
	private String role;
	
}
