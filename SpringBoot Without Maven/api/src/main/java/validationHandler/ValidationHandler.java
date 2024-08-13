//package validationHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@RestControllerAdvice
////public class ValidationHandler extends ResponseEntityExceptionHandler{
////
////	@Override
////	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
////			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
////		Map<String,String> m = new HashMap<String,String>();
////		ex.getBindingResult().getAllErrors().forEach((err) -> {
////			String f= ((FieldError) err.ge
////		}
////				
////				);
////		return super.handleMethodArgumentNotValid(ex, headers, status, request);
////	}
//}
