# About SpringBoot JPA
- Learn about [JPA](https://github.com/codophilic/Learn-Hibernate-ORM/blob/main/Theory.md#jpa-java-persistence-api)
- Lets create a SpringBoot JPA project

![alt text](image.png) 

- Add dependencies JPA and MySQL driver (here we are using MySQL database)

![alt text](image-1.png)

```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
```

- If you see , JPA is a specification which is implemented by hibernate, when we download `spring-boot-starter-data-jpa` , hibernate libraries are also provided by SpringBoot.

![alt text](image-2.png)

- Lets say we have a customer entity which has id, name and address as its value

```
package com.springboot.jpa.entities;

import jakarta.persistence.*;

@Entity
public class Customer {

	@Id
	@Column(name = "cust_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "cust_name")
	private String name;
	
	@Column(name = "cust_address")
	private String custAddress;

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

	public String getCustAddress() {
		return custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", custAddress=" + custAddress + "]";
	}
	
	
}
```

- Lets us set up a DAO Interface which will have CRUD operations with the Customer.

```
package com.springboot.jpa.entities;

import org.springframework.data.repository.CrudRepository;

public interface CustomerDaoInterface extends CrudRepository<Customer, Integer>{

	
}
```

- Hey wait, where is the CRUD operations written for the customer class? this is all hanld by **CrudRepository** interface.
- In Spring Boot, **CrudRepository** is an interface provided by SpringBoot Data JPA that simplifies the implementation of data access layers. It offers generic CRUD (Create, Read, Update, Delete) operations on a repository for a specific entity type, reducing boilerplate code and making data access easier. It integrates seamlessly with Spring Boot, making it easier to work with Spring's features like dependency injection, transactions, and more.
- **CrudRepository** is a generic interface that takes two parameters
    - The entity type the repository will manage (Customer).
    - The type of the entity’s identifier (primary key) (Integer - Wrapper Class).
- It provides several methods to perform basic CRUD operations

![alt text](image-3.png) 

- Apart from CRUD operations, SpringBoot Data JPA allows you to define custom query methods by simply declaring them in the repository interface. Spring will generate the query based on the method name.

![alt text](image-4.png) 

- **CrudRepository** should be implemented by an interface only. The whole idea is to provide a mechanism to perform CRUD operations without writing boilerplate code. SpringBoot Data JPA will automatically create a concrete class for your repository interface at runtime, so you don’t need to manually implement any methods.

- Now we need to provide DB details to SpringBoot , should we need to create any xml file? absolutely not , spring boot provides **application.properties**.

## What is application.properties?

- In a Spring Boot application, **application.properties** is a file used to configure various settings for your application. It is typically located in the `src/main/resources` directory. This file allows you to set key-value pairs that configure different aspects of your application, such as database settings, server ports, logging levels, customize any configuration and more.
- Spring Boot automatically loads and reads the **application.properties** file at startup. It uses the properties defined in this file to configure various parts of the application. The framework has built-in mechanisms to look for this file in the `src/main/resources` directory or on the classpath.
- Discover all the properties [here](https://docs.spring.io/spring-boot/appendix/application-properties/index.html)
- **application.properties** is a central place for configuring your Spring Boot application. Spring Boot automatically reads this file at startup and uses the properties defined within it to configure various components of your application. The flexibility and simplicity of application.properties make it a powerful tool for managing your application's configuration.



- For SpringBoot JPA we need to specify db details in application.properties.

```
spring.application.name=demo
spring.datasource.url=jdbc:mysql://localhost:3306/springbootjpa
spring.datasource.username=root
spring.datasource.password=Meetpandya40@
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform = org.hibernate.dialect.MySQL8Dialect
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto = create
```

- If you see STS provides suggestion while performing configuration in application.properties

![alt text](image-5.png)

- Now lets perform some CRUD operations using Springboot and JPA. Lets create and fetch users. Below is the MainMethod

```
package com.springboot.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.springboot.jpa.dao.CustomerDaoInterface;
import com.springboot.jpa.entities.Customer;

@SpringBootApplication
public class MainMethod {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(MainMethod.class, args);
		
		/**
		 * SpringBoot JPA automatically configures bean into IOC container
		 */
		CustomerDaoInterface cdi=context.getBean(CustomerDaoInterface.class);
		
		Customer c=new Customer();
		c.setName("Harsh");
		c.setCustAddress("Mumbai");
		
		/**
		 * Saving a single entity
		 */
		Customer cresult=cdi.save(c);
		System.out.println("Customer details saved: "+cresult.toString());

		Customer c1=new Customer();
		c1.setName("Meet");
		c1.setCustAddress("Delhi");
		
		Customer c2=new Customer();
		c2.setName("Jeet");
		c2.setCustAddress("MP");
		
		/**
		 * Saving multiple entities
		 */
		Iterable<Customer> allCustomers=cdi.saveAll(List.of(c1,c2));
		
		allCustomers.forEach(allcust->{
			System.out.println("Customer details saved: "+allcust.toString());
		});
		
		/**
		 * Fetch by ID
		 */
		Optional<Customer> opt=cdi.findById(3);
		System.out.println("Fetch Customer Details by ID: "+opt.get());
		
		// If ID not found then it returns null
		opt=cdi.findById(4);
		if(!opt.isPresent()) {
			System.out.println("Fetch Customer Details not found for ID 4");
		}
		
		/**
		 * Fetch all user
		 */
		allCustomers=cdi.findAll();
		System.out.println("All Customer Details");
		allCustomers.forEach(allcust->{
			System.out.println(allcust.toString());
		});

	}

}

Output:
Customer details saved: Customer [id=1, name=Harsh, custAddress=Mumbai]
Customer details saved: Customer [id=2, name=Meet, custAddress=Delhi]
Customer details saved: Customer [id=3, name=Jeet, custAddress=MP]
Fetch Customer Details by ID: Customer [id=3, name=Jeet, custAddress=MP]
Fetch Customer Details not found for ID 4
All Customer Details
Customer [id=1, name=Harsh, custAddress=Mumbai]
Customer [id=2, name=Meet, custAddress=Delhi]
Customer [id=3, name=Jeet, custAddress=MP]
```

![alt text](image-6.png)

- Lets fetch customer details by address, should we need to write any custom query? nope , jpa provides those methods you only need to add in your interface.

![alt text](image-7.png) 

- Below is the main method

```
package com.springboot.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.springboot.jpa.dao.CustomerDaoInterface;
import com.springboot.jpa.entities.Customer;

@SpringBootApplication
public class MainMethod {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(MainMethod.class, args);
		
		/**
		 * SpringBoot JPA automatically configures bean into IOC container
		 */
		CustomerDaoInterface cdi=context.getBean(CustomerDaoInterface.class);

		/**
		 * Fetch by address
		 * Here springboot jpa provides a customized inbuild method
		 */
		List<Customer> allCustDetailsForMumbai=cdi.findByCustAddress("Mumbai");
		allCustDetailsForMumbai.forEach(i->{
			System.out.println(i.toString());
		});

	}

}

Output:
Customer [id=1, name=Harsh, custAddress=Mumbai]
```

- Lets check update and delete operations, below is the main method

```
package com.springboot.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.springboot.jpa.dao.CustomerDaoInterface;
import com.springboot.jpa.entities.Customer;

@SpringBootApplication
public class MainMethod {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(MainMethod.class, args);
		
		/**
		 * SpringBoot JPA automatically configures bean into IOC container
		 */
		CustomerDaoInterface cdi=context.getBean(CustomerDaoInterface.class);
		
		/**
		 * Update Customer details using Id
		 */
		Optional<Customer >opt=cdi.findById(3);
		Customer c3=opt.get();
		c3.setName("Donga");
		cdi.save(c3);
		System.out.println("Updated user details: "+cdi.findById(3).get());
		
		/**
		 * Delete user by ID
		 */
		cdi.deleteById(3);
		System.out.println("Deleted user of ID 3:"+cdi.findById(3).isPresent());
		
		/**
		 * Delete All Users
		 */
		cdi.deleteAll();
		

	}

}

Output:
Updated user details: Customer [id=3, name=Donga, custAddress=MP]
Deleted user of ID 3:false
```

![alt text](image-8.png)

- Thus **CrudRepository** is an interface that provides a set of methods for performing basic CRUD operations on an entity, and it should be used by creating an interface that extends it. SpringBoot handles the implementation details, allowing you to work with data in a simple and efficient way.

- Lets say you want to perform custom queries, there are two ways you can achieve this.

1. Using Derived Query Methods (Method Query Creation)
    -  You define query methods by following a naming convention in your repository interface. When you start typing a method name with find, read, query, or similar keywords, your IDE (like STS) will suggest possible methods based on the entity's attributes.
    - Spring Data JPA automatically interprets the method names and generates the appropriate query behind the scenes.
    - You don't need to write any SQL or JPQL which is used in `@Query` annotation; the query is derived from the method name itself.
    - Here is the reference for other types of [derived queries](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html#jpa.query-methods.query-creation)

<video controls src="Images/springjpa/DerivedCustomQuery.mp4" title="Title"></video>

```
package com.springboot.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.springboot.jpa.dao.CustomerDaoInterface;
import com.springboot.jpa.entities.Customer;

@SpringBootApplication
public class MainMethod {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(MainMethod.class, args);
		
		/**
		 * SpringBoot JPA automatically configures bean into IOC container
		 */
		CustomerDaoInterface cdi=context.getBean(CustomerDaoInterface.class);
		
		/**
		 * Custom Query
		 */	
		Customer cust1=new Customer();
		cust1.setName("Harsh Pandya");
		cust1.setCustAddress("Mumbai");
		Customer cust2=new Customer();
		cust2.setName("Meet Pandya");
		cust2.setCustAddress("Mumbai");
		Customer cust3=new Customer();
		cust3.setName("Harsh Donga");
		cust3.setCustAddress("Mumbai");
		
		cdi.saveAll(List.of(cust1,cust2,cust3));

		List<Customer> customerfound=cdi.findByNameAndCustAddress("Harsh Pandya", "Mumbai");
		System.out.println("Finding details using method findByNameAndCustAddress()");
		customerfound.forEach(i->{
			System.out.println(i);
		});

	}

}

Output:
Customer [id=4, name=Harsh Pandya, custAddress=Mumbai]
```

2. Using `@Query` annotation
    - The `@Query` annotation in Spring Data JPA is used to define custom database queries directly within your repository interface. It allows you to write JPQL (Java Persistence Query Language, ORM based queries) or native SQL queries, giving you more flexibility to retrieve, update, or delete data that doesn't fit the typical query methods generated by method names.
    - The `@Param` annotation in Spring Data JPA binds the method parameter to the named parameter in the `@Query`. It allows you to pass input values into the query, ensuring that the method argument is correctly mapped to the query parameter.
    - Flexibility: Allows for more complex queries that can't be derived from method names.
    - Control: You have full control over the query's content and execution.
    - Custom Logic: You can incorporate database-specific features or complex joins, aggregations, etc.
    - Below is the CustomerDaoInterface and Main method details

```
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


package com.springboot.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.springboot.jpa.dao.CustomerDaoInterface;
import com.springboot.jpa.entities.Customer;

@SpringBootApplication
public class MainMethod {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(MainMethod.class, args);
		
		/**
		 * SpringBoot JPA automatically configures bean into IOC container
		 */
		CustomerDaoInterface cdi=context.getBean(CustomerDaoInterface.class);
				
		/**
		 * Custom Query
		 */	
		Customer cust1=new Customer();
		cust1.setName("Harsh Pandya");
		cust1.setCustAddress("Mumbai");
		Customer cust2=new Customer();
		cust2.setName("Meet Pandya");
		cust2.setCustAddress("Mumbai");
		Customer cust3=new Customer();
		cust3.setName("Harsh Donga");
		cust3.setCustAddress("Mumbai");
		
		cdi.saveAll(List.of(cust1,cust2,cust3));
		
		/**
		 * JPQL
		 */
		List<Customer> customerfound = cdi.fetchUsingLowerCaseNameAndAddress("Harsh Pandya".toUpperCase(), "Mumbai".toUpperCase());
		System.out.println("Finding details using method fetchUsingLowerCaseNameAndAddress()");
		customerfound.forEach(i->{
			System.out.println(i);
		});

		/**
		 * Native SQL
		 */
		Customer thirdCustomer=cdi.retrieveThirdCustomerOnly();
		System.out.println("Third Customer Details: "+thirdCustomer.toString());
	}

}

Output:
Finding details using method fetchUsingLowerCaseNameAndAddress()
Customer [id=1, name=Harsh Pandya, custAddress=Mumbai]
Third Customer Details: Customer [id=3, name=Harsh Donga, custAddress=Mumbai]
```




















