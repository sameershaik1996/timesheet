package us.redshift.timesheet.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;


//@ControllerAdvice
public class CustomSQLException extends ResponseEntityExceptionHandler implements Serializable {

    @ExceptionHandler(value
            = {DataIntegrityViolationException.class, SQLIntegrityConstraintViolationException.class})
    protected ResponseEntity<?> handleConflict(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getCause().getCause().getLocalizedMessage(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
