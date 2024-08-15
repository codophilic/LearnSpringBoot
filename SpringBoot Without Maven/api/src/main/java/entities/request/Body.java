package entities.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Body {

	/**
	 * Here we have not used @JsonProperty because variable name is same as json keys 
	 */
	@Valid
    @NotNull(message = "Username cannot be null")
    @Size(min = 3, message = "Username must be at least 3 characters long")
    private String username;

	@Valid
    @NotNull(message = "ID cannot be null")
    @Max(value = 99999, message = "ID must be less than or equal to 5 digits")
    private Integer id;
	
	@Valid
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

	@Valid
    @NotNull(message = "Address cannot be null")
    @Size(min = 10, message = "Address must be at least 10 characters long")
    private String address;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
