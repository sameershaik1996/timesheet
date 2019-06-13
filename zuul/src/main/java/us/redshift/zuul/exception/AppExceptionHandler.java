package us.redshift.zuul.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AppExceptionHandler {

   /* @ResponseBody
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity <?> handleCustomException(CustomException exception, HttpServletRequest res) {
        //System.out.println.println("hi");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }*/

}