package xoriant.ProductManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class MyExceptionHandler {

	 @ExceptionHandler(value = ProductNotfoundException.class)
	   public ResponseEntity<Object> exception(ProductNotfoundException exception) {
		 
	      return new ResponseEntity<>("Product Is Not Avaialable", HttpStatus.NOT_FOUND);
	   }
	 	 
	 @ExceptionHandler(value = NullOrEmptyExceptionClass.class)
	   public ResponseEntity<Object> exception(NullOrEmptyExceptionClass exception) {
		 
	      return new ResponseEntity<>("Product Field should not empty", HttpStatus.NOT_FOUND);
	   }
		
}
