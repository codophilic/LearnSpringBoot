package com.eazybytes.eazyschool.controller;

import java.util.List;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.eazyschool.model.IDNameCollections;

@RestController
public class RestrictedController {

	@PreAuthorize("hasAuthority('read')")
	@GetMapping("/notallowed")
	public String restricted(String defaultAdminUser) {
		return "Access Granted";
	}
	
	
	@GetMapping("/allidname")
	@PreFilter("filterObject.name != 'Harsh' && filterObject.name != 'Meet'")
	@PostFilter("filterObject.name != 'Test'")
	public List<IDNameCollections> idnameCollectionsMethod(@RequestBody List<IDNameCollections> allcollections){
		IDNameCollections obj1= new IDNameCollections();
		obj1.setId(1);
		obj1.setName("Test");
		IDNameCollections obj2= new IDNameCollections();
		obj2.setId(2);
		obj2.setName("Jeet");
		allcollections.add(obj1);
		allcollections.add(obj2);
		return allcollections;
	}
}
