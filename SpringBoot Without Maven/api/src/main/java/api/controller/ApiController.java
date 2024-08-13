package api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import entities.request.PDFRequest;
import entities.response.PDFResponse;
import jakarta.validation.Valid;

@RestController
public class ApiController {

	@GetMapping("/sample")
	public String sampleMethod() {
		return "This is a sample method";
	}
	
	@PostMapping("/pdfapi")
	public ResponseEntity<PDFResponse> apiRequest(@Valid @RequestBody PDFRequest pdfRequest) {
//		// Check if there are validation errors
//		System.out.println(bindingResult.hasErrors());
//        if (bindingResult.hasErrors()) {
//            // Extract the first validation error
//            String failureMessage = bindingResult.getFieldError().getDefaultMessage();
//            String customCode = "400"; // Custom code
//
//            // Construct the response
//            PDFResponse pdfResponse = new PDFResponse();
//            pdfResponse.setHeaders(pdfRequest.getHeaders());
//            pdfResponse.setBody(new PDFResponse.Body(null, failureMessage, customCode));
//            System.out.println("ERRORS........");
//            return new ResponseEntity<>(pdfResponse, HttpStatus.BAD_REQUEST);
//        }

        // If no errors, proceed with the normal logic
        // Example: Assuming some process generates an "encode" string
        String encode = "263he93ne9...";

        PDFResponse pdfResponse = new PDFResponse();
        pdfResponse.setHeaders(null);
        pdfResponse.setBody(new PDFResponse.Body(encode, null, "200"));

        return new ResponseEntity<>(pdfResponse, HttpStatus.OK);
	}
}
