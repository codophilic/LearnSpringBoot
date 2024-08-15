package entities.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Headers {
	@Valid
    @NotNull(message = "RequestID cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "RequestID must be alphanumeric")
    @JsonProperty("RequestID")
    private String requestID;
    
	@Valid
    @NotNull(message = "RequestInfo cannot be null")
    @Pattern(regexp = "^User Details$", message = "RequestInfo must be 'User Details'")
    @JsonProperty("RequestInfo")
    private String requestInfo;

    // Getters and Setters
    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }
}
