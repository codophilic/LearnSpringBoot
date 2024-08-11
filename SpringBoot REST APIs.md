# About REST APIS
- A RESTful (REpresentational State Transfer) (REST) API is an architectural style for an application programming interface that uses HTTP requests to access and use data. That data can be used to GET , PUT , POST and DELETE data types, which refers to reading, updating, creating and deleting operations related to resources.

![alt text](image.png)

- APIs can be considered a type of web application, but their role is different. They are more focused on data exchange and enabling interactions between different software systems rather than directly providing a user interface.
- Traditional web applications are designed to deliver web pages (HTML, CSS, JavaScript) that users can interact with via a web browser. They typically involve a frontend (the user interface) and a backend (the server-side logic). When a user interacts with a traditional web application (e.g., by clicking a button), the server processes the request and returns a complete web page or updates a portion of the page.
- An API is essentially the backend part of a web application that exposes certain functionality or data to be consumed by other applications, whether they are web, mobile, or other types of clients. Instead of returning a full web page, an API returns raw data, usually in formats like JSON or XML. This data can be consumed by clients, which then decide how to present or use the data.
- Traditional web applications are user-facing, meaning they are directly interacted with by users through a browser. APIs are typically not directly interacted with by end-users but rather by other software (like a frontend application or another backend service). Web applications return full HTML pages, while APIs return data.

# REST APIS using SpringBoot

- Lets create a simple API and understand about it. To start with the process install the dependencies.

![alt text](image-1.png)

- Post dependencies installation we can see our SpringBoot Application

```
package com.springboot.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}

}
```

- On browser 

![alt text](image-2.png) 

- On Postman tool

![alt text](image-3.png)



- Lets create an **ApiController** and execute it

```
package com.springboot.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {

	@RequestMapping(path="/sample",method = RequestMethod.GET)
	@ResponseBody
	public String sampleMethod() {
		return "This is a sample response";
	}
}
```

- In Spring MVC, when you return a view name (like a JSP or HTML file), Spring looks for that file and renders it as the response. This is typical for web applications where you want to generate HTML pages that are displayed in a browser.
- However, when you're building RESTful web services, you often want to return data (like JSON, XML, or plain text) rather than a view. This is where the `@ResponseBody` annotation comes into play.
- When you annotate a method with `@ResponseBody`, the return value of that method is written directly to the HTTP response body. So instead of trying to render a view, Spring will send the data (like a string, JSON, or XML) directly to the client.
- Spring automatically converts the return value into the appropriate format. For example, if you return an object, Spring can convert it to JSON or XML (depending on your configuration) before sending it to the client. Default it is plain text.

## Spring Boot with REST APIs Flow
- **Client Request**: A client (could be a browser, mobile app, or another server) sends an HTTP request.
- **DispatcherServlet**: Just like in Spring MVC, the request goes to the DispatcherServlet.
- **Controller**: The request is forwarded to a controller method. However, in a REST API, the controller method usually returns data rather than a view name.
- **@ResponseBody**: When the method is annotated with `@ResponseBody`, the return value is automatically serialized (converted) into a format like JSON or XML and written directly to the HTTP response body. There’s no need for a view resolver because no view is being rendered; instead, raw data (like JSON) is returned. Default plain text is return if nothing specified
- **Response**: The serialized data is sent back to the client as the response. This data can be consumed by any client capable of handling JSON or XML.

![alt text](image-4.png)

- So whenever you are creating a new method along with the `@RequestMapping` you need to write `@ResponseBody` multiple times. 
- What if `@Controller` and `@ResponseBody` are combined and you don't need to write it repetitively the `@ResponseBody`? , we have an annotation `@RestController`.

```
package com.springboot.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class ApiController {

	/*
	 * @RequestMapping(path="/sample",method = RequestMethod.GET)
	 * 
	 * @ResponseBody public String sampleMethod() { return
	 * "This is a sample response"; }
	 */
	
	@GetMapping("/sample")
	public String sampleMethod() {
		return "This is a sample response";
	}
}
```

- `@Controller` annotation is used to define a Spring MVC controller. It is typically used in web applications where the response might be a view (like an HTML page).
- `@RestController` is a specialized version of @Controller. It combines `@Controller` and `@ResponseBody` into one annotation. When you use `@RestController`, every method in the controller automatically behaves as if it were annotated with `@ResponseBody`.
- `@GetMapping` is a shorthand for `@RequestMapping(method = RequestMethod.GET)`. It is more concise and often preferred when defining GET endpoints.

- Lets create a simple Employee Class which has employee ID, name, department.




















































































