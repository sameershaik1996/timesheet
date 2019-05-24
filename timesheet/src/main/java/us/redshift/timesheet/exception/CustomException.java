package us.redshift.timesheet.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;


@ControllerAdvice
public class CustomException extends ResponseEntityExceptionHandler implements Serializable {


    public CustomException() {
        super();
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class, SQLIntegrityConstraintViolationException.class})
    protected ResponseEntity<?> handleConflict(RuntimeException ex, WebRequest request) {
        ErrorMessage errorObj = new ErrorMessage(new Date(), HttpStatus.CONFLICT.value(), ex.getCause().getCause().getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//        return handleExceptionInternal(ex, ex.getCause().getCause().getLocalizedMessage(),
//                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<?> handleResourceNotFound(RuntimeException ex, WebRequest request) {
        ErrorMessage errorObj = new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }


}
