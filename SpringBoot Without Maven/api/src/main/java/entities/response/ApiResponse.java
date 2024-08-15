package entities.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import entities.request.Body;
import entities.request.Headers;

public class ApiResponse {

	@JsonProperty("Headers")
	private Headers headers;
	
	@JsonProperty("Body")
	private Body body;
	
	@JsonProperty("Status")
	private Status status;

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
