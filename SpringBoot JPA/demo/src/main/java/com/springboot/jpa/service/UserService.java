package com.springboot.jpa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.springboot.jpa.dao.UserDaoInterface;
import com.springboot.jpa.entities.User;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDaoInterface udi;

    @PersistenceContext
    private EntityManager entityManager;
    
	@Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveAndRetrieveUser() {
        User u = new User();
        u.setName("Harsh");
        u.setAge(24);

        // Save the entity
        udi.save(u);

    	String sqlQuery = "SELECT * FROM user_details WHERE user_id = ?";
    	List<User> customers = jdbcTemplate.query(sqlQuery,new Object[]{1}, new BeanPropertyRowMapper(User.class));
    	
    	if (!customers.isEmpty()) {  
    		  System.out.println("User found with id: " + customers.get(0).getId());  
    		} else {  
    		  System.out.println("User not found when used native query");  
    		}

        /* Attempt to retrieve the entity
         * When we save entity is gets save into persistance context
         * and not into database.
         */
        Optional<User> uget = udi.findById(1);
        if(uget.isPresent()) {
        	System.out.println("User Details found without using native query");
        }else {
        	System.out.println("User Details not found");
        }
        
        entityManager.flush();
    }
    
    public void saveAndFlushRetrieveUser() {
        User u = new User();
        u.setName("Meet");
        u.setAge(21);

        // Save the entity
        udi.saveAndFlush(u);

    	String sqlQuery = "SELECT * FROM user_details WHERE user_id = ?";
    	List<User> customers = jdbcTemplate.query(sqlQuery,new Object[]{2}, new BeanPropertyRowMapper(User.class));
    	
    	if (!customers.isEmpty()) {  
    		  System.out.println("User found using native query, id: " + customers.get(0).getId());  
    		} else {  
    		  System.out.println("User not found when used native query");  
    		}

        /* Attempt to retrieve the entity
         * When we save entity is gets save into persistance context
         * and not into database.
         */
        Optional<User> uget = udi.findById(1);
        if(uget.isPresent()) {
        	System.out.println("User Details found without using native query");
        }else {
        	System.out.println("User Details not found");
        }
        
        entityManager.flush();
    }
    
    public void saveAll(List<User> userList) {
    	udi.saveAll(userList);
    }
    
    public void deleteAllDetails() {
    	udi.deleteAll();
    }
    
    public void deleteAllDetailsInBatch() {
    	udi.deleteAllInBatch();
    }
    
    public List<User> fetchRecordsOFAgeGreaterThanEqual(int age){
    	return udi.findByAgeGreaterThanEqual(age);
    }
    
    public void deleteDetailsOfParticularEntities(List<User> entitiesDetails) {
    	udi.deleteAllInBatch(entitiesDetails);
    }
}