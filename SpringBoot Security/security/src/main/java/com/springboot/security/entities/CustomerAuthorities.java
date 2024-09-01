package com.springboot.security.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "customer_authorities")
@Getter
@Setter
@ToString
public class CustomerAuthorities {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int uniqueid;
	
	private String authority;
	
	@ManyToOne
	@JoinColumn(name="foreignkey_cust_id", referencedColumnName = "id")
	private Customer customer;
}
