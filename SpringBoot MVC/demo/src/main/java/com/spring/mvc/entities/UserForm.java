package com.spring.mvc.entities;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserForm {

	/**
	 * NotBlank - Ensures that the field is not null and that the trimmed length of the string is greater than 0 (i.e., it's not empty).
	 * Size - Validates that the length of the string is within the specified bounds.
	 */
	@NotBlank( message = "User name cannot be empty" )
	@Size(max = 10,min = 3, message = "User name must be between 3 - 10 characters")
    private String name;
	
	@NotBlank(message = "Email ID cannot be blank")
    private String email;
	
	/**
	 * Max - Ensures that the numeric value of the field is less than or equal to the specified maximum.
	 */
	@Max(value = 24, message = "Age cannot be greater than 25")
    private int age;
	
	/**
	 * Pattern - Validates that the string matches the specified regular expression.
	 */
	@Pattern(regexp = "^\\d{10}$" , message = "Invalid phone number")
    private String phoneNumber;
	
	/**
	 * AssertTrue - Ensures that the field is true.
	 */
	@AssertTrue(message = "Accept terms and conditions")
    private boolean termsAccepted;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }
}
