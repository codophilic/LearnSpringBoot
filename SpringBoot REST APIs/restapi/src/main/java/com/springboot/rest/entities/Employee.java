package com.springboot.rest.entities;

import jakarta.persistence.*;

@Entity
public class Employee {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	@Column(name = "unique_id")
	private int id;
	
	@Column(name = "employee_id",unique = true)
	private int empid;
	
	@Column(name = "employee_name")
	private String name;
	
	@Column(name="employee_department")
	private String dept;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", empid=" + empid + ", name=" + name + ", dept=" + dept + "]";
	}
	
	
}
