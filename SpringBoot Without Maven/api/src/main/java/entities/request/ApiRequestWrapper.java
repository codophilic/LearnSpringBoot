package entities.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ApiRequestWrapper {

	/**
	 * If instance variable name is not exactly same as request key name, jackson library won't bind it
	 * So jackson library provides @JsonProperty which binds the Request Key name with the instance variables name
	 * even if both are not exactly same
	 */
	@Valid
    @NotNull(message = "ApiRequest cannot be null")
    @JsonProperty("ApiRequest")
    private ApiRequest apiRequest;

    // Getters and Setters
    public ApiRequest getApiRequest() {
        return apiRequest;
    }

    public void setApiRequest(ApiRequest apiRequest) {
        this.apiRequest = apiRequest;
    }
}
