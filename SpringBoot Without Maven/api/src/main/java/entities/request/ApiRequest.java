package entities.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ApiRequest {
	/**
	 * If instance variable name is not exactly same as request key name, jackson library won't bind it
	 * So jackson library provides @JsonProperty which binds the Request Key name with the instance variables name
	 * even if both are not exactly same
	 */
	@Valid
    @NotNull(message = "Headers cannot be null")
    @JsonProperty("Headers")
    private Headers headers;

	@Valid
    @NotNull(message = "Body cannot be null")
    @JsonProperty("Body")
    private Body body;

    // Getters and Setters
    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
