package api.controller;

import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import entities.request.ApiRequestWrapper;
import entities.response.ApiResponse;
import entities.response.ApiResponseWrapper;
import entities.response.Status;
import jakarta.validation.Valid;

@RestController
public class ApiController {
	
	@Value("${greetings}")
	private String customMsg;
	
	public String getCustomMsg() {
		return customMsg;
	}

	public void setCustomMsg(String customMsg) {
		this.customMsg = customMsg;
	}

	@GetMapping("/sample")
	public String sampleMethod() {
		return "This is a sample method";
	}
	
	@PostMapping("/random")
	public ResponseEntity<ApiResponseWrapper> apiRequest(@Valid @RequestBody ApiRequestWrapper request, BindingResult bindingResult) {
		ApiResponseWrapper response = new ApiResponseWrapper();
        ApiResponse apiResponse= new ApiResponse();
        apiResponse.setHeaders(request.getApiRequest().getHeaders());
        apiResponse.setBody(request.getApiRequest().getBody());
    	Status st= new Status();
    	System.out.println(bindingResult);
    	
    	/**
    	 * If any validation fails
    	 */
        if (bindingResult.hasErrors()) {
        	st.setCode("111");
            st.setMessage(bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "))); 
            st.setStatus("Validation Failed");
            st.setResponseCode(HttpStatus.PRECONDITION_FAILED.value());
            apiResponse.setStatus(st);
        	response.setApiResponse(apiResponse);
            return ResponseEntity.badRequest().body(response);
        }
        
        /**
         * Validations are success
         */
        /**
         * Generating random message
         */
        String[] messages = {
                "Hello, world!",
                "Have a great day!",
                "Keep smiling!",
                "You got this!",
                "Stay positive!"
            };

        Random random = new Random();
        int index = random.nextInt(messages.length);
        String randomMessage = messages[index];

        st.setMessage(getCustomMsg()+" "+randomMessage);
        st.setResponseCode(HttpStatus.OK.value());
        st.setCode("000");
        st.setStatus("Validation Passed");
    	apiResponse.setStatus(st);
    	response.setApiResponse(apiResponse);
        return ResponseEntity.ok(response);
    }
}
