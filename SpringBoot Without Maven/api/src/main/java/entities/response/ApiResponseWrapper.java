package entities.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponseWrapper {

	@JsonProperty("ApiResponse")
	private ApiResponse apiResponse;

	public ApiResponse getApiResponse() {
		return apiResponse;
	}

	public void setApiResponse(ApiResponse apiResponse) {
		this.apiResponse = apiResponse;
	}
	
	
}
