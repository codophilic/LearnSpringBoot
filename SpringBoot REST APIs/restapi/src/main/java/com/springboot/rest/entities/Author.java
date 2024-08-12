package com.springboot.rest.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "author_table")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "author_id")
	private int authid;
	
	@Column(name = "author_name")
	private String authName;


	public int getAuthid() {
		return authid;
	}

	public void setAuthid(int authid) {
		this.authid = authid;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}
	
	
}
