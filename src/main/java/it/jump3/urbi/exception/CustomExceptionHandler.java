package it.jump3.urbi.exception;

import brave.Span;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    public Span span;

    @Autowired
    private MessageSource messageSource;


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleError(Exception e) {

        ErrorResponse errorResponse = getResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        log.error(e.getMessage(), e);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException ex) {

        ErrorResponse error = getResponse(HttpStatus.BAD_REQUEST.value(), ex); //96
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(HttpClientErrorException ex) {

        ErrorResponse errorResponse = getResponse(ex.getStatusCode().value(), ex);
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> resourceAccessExceptionHandler(ResourceAccessException ex) {

        ErrorResponse errorResponse = getResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(HttpServerErrorException.GatewayTimeout.class)
    public ResponseEntity<ErrorResponse> exceptionGatewayTimeoutHandler(Exception ex) {
        return new ResponseEntity<>(getResponse(HttpStatus.GATEWAY_TIMEOUT.value(), ex), new HttpHeaders(), HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(HttpServerErrorException.ServiceUnavailable.class)
    public ResponseEntity<ErrorResponse> exceptionServiceUnavailableHandler(Exception ex) {
        return new ResponseEntity<>(getResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), ex), new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {

        ErrorResponse error = getResponse(HttpStatus.BAD_REQUEST.value(), getErrors(ex.getBindingResult()), ex);
        return new ResponseEntity<>(error, headers, status);
    }

    @Override
    public ResponseEntity<Object> handleBindException(BindException ex,
                                                      HttpHeaders headers,
                                                      HttpStatus status,
                                                      WebRequest request) {

        ErrorResponse error = getResponse(HttpStatus.BAD_REQUEST.value(), getErrors(ex.getBindingResult()), ex);
        return new ResponseEntity<>(error, headers, status);
    }

    private List<String> getErrors(BindingResult bindingResult) {

        return bindingResult
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

    public ErrorResponse getResponse(int code, Exception ex) {
        return ErrorUtils.getResponse(messageSource, span, code, ex);
    }

    public ErrorResponse getResponse(int code, List<String> msgs, Exception ex) {
        return ErrorUtils.getResponse(messageSource, span, code, msgs, ex);
    }
}
