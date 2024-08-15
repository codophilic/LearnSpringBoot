package exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


import entities.response.Status;

import org.springframework.http.converter.HttpMessageNotReadableException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/**
	 * @ControllerAdvice or @RestControllerAdvice
	 *  - This allows you to handle exceptions globally across all your controllers.
		- When an exception is thrown from a controller, Spring Boot looks for a method annotated with @ExceptionHandler inside classes annotated with @ControllerAdvice.
		- @ExceptionHandler(HttpMessageNotReadableException.class) catches errors related to invalid JSON.
	 */

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleInvalidJsonException(HttpMessageNotReadableException ex) {
        Status st= new Status();
        st.setResponseCode(HttpStatus.BAD_REQUEST.value());
        st.setMessage("Invalid Json");
        st.setCode("999");
        st.setStatus("Failure");
        return new ResponseEntity<>(st, HttpStatus.BAD_REQUEST);
    }

    // Add more handlers if needed
}
