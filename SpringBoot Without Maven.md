# SpringBoot Without Maven
- Lets create a project in SpringBoot without using Maven build automation tool and deploy it on the external tomcat server.
- In this project we will replace PDF placeholders by text values or images using REST Apis
- We will be create a Rest Api, which will take input parameters from the request and replace the placeholders by values using request key's value.
- Lets create a simple application form PDF.

![alt text](image.png)

- Using , create placeholders in PDF

![alt text](image-1.png)

- This is how Update PDF with placeholders looks like.

![alt text](image-2.png)

- So basically `First Name Placeholder` field placeholder will be replace via a value which is given in the api request.

- Lets create a new dynamic web project in eclipse. During new project creation we won't generate **web.xml** file , as SpringBoot will perform all the autoconfigurations.

![alt text](image-3.png)

![alt text](image-4.png)

- Since we are not using maven we need to download the dependencies manually, so lets first create a folder with name **SpringBoot Dependencies**, okay what all dependencies needs to be added? well , you can follow the below steps to get your dependencies.

1. Create a temporary workspace of SpringBoot project using maven
2. In pom.xml add your require dependencies
3. Run and test your temporary project
4. Export the dependencies but using maven command `dependency:copy-dependencies`. The dependencies will get copied under a folder `/target`

<video controls src="20240813-1638-24.9055760.mp4" title="Title"></video>

- Now you can copy these dependencies into your main workspace and delete the temporary workspace.

>[!NOTE]
> - There could be multiple jar files which are not used by SpringBoot. Since it is an opinionated tool it defines a standard structure and downloads all the jars.
> - On the basis of trail and error by excluding jar/jars we can check if our SpringBoot is working correct or not.

- So i have copy the required dependencies used by springboot under folder **springboot dependencies** and unused dependencies to folder **springboot unused dependencies**.

>[!NOTE]
> - In this project we won't be using any operations wrto database, so those jars are excluded and added into **springboot unused dependencies**.

- Add this **springboot dependencies** in classpath as well as in the deployment assembly.

<video controls src="20240813-1700-07.5911933.mp4" title="Title"></video>

- Now under `src/main/java` folder create a main method and annotate it with `@SpringBootApplication`.

```
package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringBootMainApplication extends SpringBootServletInitializer {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootMainApplication.class);
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootMainApplication.class, args);

	}

}
```

- `@SpringBootApplication` is a convenience annotation that combines three annotations, `@EnableAutoConfiguration`, `@ComponentScan`, and `@Configuration`. It tells Spring Boot to start adding beans based on the classpath settings, other beans, and various property settings.
- **SpringBootServletInitializer** class is required when you're deploying your Spring Boot application to an external servlet container (like Tomcat) instead of using the embedded one. By extending **SpringBootServletInitializer**, you're telling Spring Boot that your application will be deployed as a traditional WAR file to a servlet container.

```
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootMainApplication.class);
	}
```

- It is specifically needed when you are deploying the application as a WAR file. **SpringApplicationBuilder** is used to build the Spring application context. By specifying `builder.sources(SpringBootMainApplication.class);`, you're telling Spring Boot to use the **SpringBootMainApplication** class as the main configuration class for the application.

- Lets create a ApiController which will be our `@RestController`, under package `src/main/java/api`.

```
package api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	@GetMapping("/sample")
	public String sampleMethod() {
		return "This is a sample method";
	}
}
```

- Lets run this project with **Spring Boot App**, Run As -> Spring Boot App

![alt text](image-5.png)

- Wait where is **application.properties**? , you need to add that manually, lets create a folder with name **resources** under `src/main` Create manually **application.properties**. Post creation , lets configure our server port to 8085. When we create any property , we need to add that in the classpath.

<video controls src="20240813-1733-09.0005941.mp4" title="Title"></video>

- Now lets run again our application

![alt text](image-6.png)

>[!IMPORTANT]
> - Here we are still using embedded tomcat over here. When we are working on our dev , we can use embedded tomcat, suppose we wanted to deploy it on any external web server, we can export the war file of it and deploy that on the external web server.

- Lets export the war file and deploy that on tomcat server.
- In eclipse , right click on your project -> export -> WAR file.

![alt text](image-7.png)

- There are two ways to deploy a war file on tomcat.

#### Using CLI

- Copy war file to `apache-tomcat-version/webapps`.
- Start **start.bat** file under folder `/bin`.

<video controls src="20240813-1800-59.5465388.mp4" title="Title"></video>

#### Using Frontend

- To deploy war file using frontend, first you need to configure user name and password under **tomcat-user.xml** file in `apache-tomcat-version/conf`.

```
<user username="root" password="root" roles="manager-gui,manager-script" />
```

- When you want to manage Tomcat, including deploying applications through the Tomcat Manager web interface (the frontend screen), you need to configure users with the appropriate roles in the **tomcat-users.xml** file. This role allows you to access the Tomcat Manager web interface through the browser, where you can deploy, undeploy, start, stop, and manage applications.

<video controls src="20240813-1806-34.9063792.mp4" title="Title"></video>

- If you see in our **application.properties** the port number used was 8085, but when we deploy on tomcat the port number is 8080 because when you deploy your Spring Boot application as a WAR file on an external Tomcat server, the port number is determined by the Tomcat server itself, not by your Spring Boot application’s configuration.
- Spring-specific configurations will still apply when deploying on an external Tomcat server. However, server-specific configurations, like the port, will be managed by the Tomcat server itself.
- When we deploy war file on external server, the war file gets extracted. We can see the contents like classes, libraries, properties etc.. 

<video controls src="20240813-1813-23.9669885.mp4" title="Title"></video>


- Uptil now we have successfully create a simple springboot rest api project and deployed that into external web server. Lets do code for replacing placeholders values of PDF.
- For that we need to download **iText** dependency jar and configure that in the classpath and deployment assembly.
- Post that 





