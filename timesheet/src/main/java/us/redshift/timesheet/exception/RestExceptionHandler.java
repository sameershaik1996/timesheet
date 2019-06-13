package us.redshift.timesheet.exception;

import org.hibernate.JDBCException;
import org.hibernate.PersistentObjectException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice("us.redshift")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
    }


    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getConstraintViolations());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
    }

    /**
     * Handle HttpMessageNotWritableException.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Error writing JSON output";
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    /**
     * Handle NoHandlerFoundException.
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     */
    /*@ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                  WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,ex.getCause().getLocalizedMessage(), ex));
    }*/


    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> handleNoSuchElement(
            NoSuchElementException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


    @ExceptionHandler(value
            = {DataIntegrityViolationException.class, SQLIntegrityConstraintViolationException.class})
    protected ResponseEntity<?> handleConflict(RuntimeException ex, WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getCause().getCause().getLocalizedMessage(), ex);
        if (ex.getCause().getCause() != null) {
            if (ex.getCause().getCause().getLocalizedMessage().contains("Duplicate")) {
                //System.out.println("inside the exception if");

                Pattern regex = Pattern.compile("'(.*?)'");
                Matcher regexMatcher = regex.matcher(ex.getCause().getCause().getLocalizedMessage());
//                //System.out.println(regexMatcher.groupCount());

                List<String> matchList = new ArrayList<String>();
                while (regexMatcher.find()) {
                    if (regexMatcher.group(0) != null) {
                        matchList.add(regexMatcher.group(0));
                    }
                }
//                //System.out.println(matchList);

                apiError.setMessage(matchList.get(0) + " Already Exist");
                apiError.setDebugMessage(ex.getLocalizedMessage());
            }
        }


        return buildResponseEntity(apiError);
    }


    @ExceptionHandler(PersistentObjectException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> handlePersistentObjectException(HttpServletRequest request, PersistentObjectException exception) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setDebugMessage(exception.getMessage());
        apiError.setMessage("Please Refresh the Page and Try Again");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex,
                                                                    WebRequest request) throws JSONException {
        //System.out.println(ex.getResponseBodyAsString());
        JSONObject jsonObject = new JSONObject(ex.getResponseBodyAsString()).getJSONObject("apierror");
        //System.out.println(jsonObject.getString("message"));
        ApiError apiError = new ApiError(ex.getStatusCode());
        apiError.setMessage(jsonObject.getString("message"));
        apiError.setDebugMessage(jsonObject.getString("debugMessage"));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(JDBCException.class)
    protected ResponseEntity<Object> handleJDBCException(JDBCException ex,
                                                         WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getCause().getCause().getLocalizedMessage(), ex));
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> handleEmptyDataException(HttpServletRequest request, Exception exception) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(exception.getMessage());
        return buildResponseEntity(apiError);
    }


    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> handleValidationException(HttpServletRequest request, Exception exception) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(exception.getMessage());
        return buildResponseEntity(apiError);
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}