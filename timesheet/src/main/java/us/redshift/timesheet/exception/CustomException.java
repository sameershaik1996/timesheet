package us.redshift.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;
import java.util.Date;


//@ControllerAdvice
public class CustomException extends ResponseEntityExceptionHandler implements Serializable {


    public CustomException() {
        super();
    }


    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<?> handleResourceNotFound(RuntimeException ex, WebRequest request) {
        ErrorMessage errorObj = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
                request.getDescription(false));
        return buildResponseEntity(errorObj);
    }

    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<?> handleValidationError(RuntimeException ex, WebRequest request) {
        ErrorMessage errorObj = new ErrorMessage(new Date(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                request.getDescription(false));
        return buildResponseEntity(errorObj);
    }


    private ResponseEntity<Object> buildResponseEntity(ErrorMessage errorMessage) {
        return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(errorMessage.getCode()));
    }


}
