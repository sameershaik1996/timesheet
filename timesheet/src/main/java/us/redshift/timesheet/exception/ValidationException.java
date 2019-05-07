package us.redshift.timesheet.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class ValidationException extends RuntimeException implements Serializable {

    private String msg;

    public ValidationException(String msg) {
        super(String.format("%s", msg));
        this.msg = msg;
    }
}
