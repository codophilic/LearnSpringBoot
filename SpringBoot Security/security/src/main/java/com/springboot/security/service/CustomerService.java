package com.springboot.security.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.security.dao.CustomerDao;
import com.springboot.security.entities.Customer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService{
	
	private final CustomerDao customerDao;

	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String userEmailId) throws UsernameNotFoundException {
		 Customer exists=customerDao.findByEmailid(userEmailId);
		 if(exists==null) {
			 throw new UsernameNotFoundException("Customer Email Id - "+userEmailId+" does not exists");
		 }
		 
		 List<GrantedAuthority> grantedRoles = List.of(new SimpleGrantedAuthority(exists.getRole()));
		 return new User(exists.getEmailid(),exists.getPwd(),grantedRoles);
	}

	public Customer saveCustomerDetails(Customer customer) {
		String hashpwd=passwordEncoder.encode(customer.getPwd());
		customer.setPwd(hashpwd);
		return customerDao.save(customer);
	}
	
}
