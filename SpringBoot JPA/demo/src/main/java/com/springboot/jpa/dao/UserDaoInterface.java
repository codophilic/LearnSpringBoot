package com.springboot.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.jpa.entities.User;

public interface UserDaoInterface extends JpaRepository<User,Integer> {
	
	List<User> findByAgeGreaterThanEqual(int agecriteria);
}
