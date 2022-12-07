package io.locngo.support.portal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.locngo.support.portal.domain.HttpResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ UsernameAlreadyExistedException.class })
    public ResponseEntity<HttpResponse> handleUsernameAlreadyExistedException(
        UsernameAlreadyExistedException exception
    ) {
        log.error(exception.getLocalizedMessage());

        final HttpResponse response = this.createHttpResponse(HttpStatus.CONFLICT, exception);

        return new ResponseEntity<HttpResponse>(response, response.getStatus());
    }

    @ExceptionHandler({ EmailAlreadyExistedException.class })
    public ResponseEntity<HttpResponse> handleEmailAlreadyExistedException(
        EmailAlreadyExistedException exception
    ) {
        log.error(exception.getLocalizedMessage());

        final HttpResponse response = this.createHttpResponse(HttpStatus.CONFLICT, exception);

        return new ResponseEntity<HttpResponse>(response, response.getStatus());
    }

    @ExceptionHandler({ UsernameNotFoundException.class })
    public ResponseEntity<HttpResponse> handleUsernameNotFoundException(
        UsernameNotFoundException exception
    ) {
        log.error(exception.getLocalizedMessage());

        final HttpResponse response = this.createHttpResponse(HttpStatus.NOT_FOUND, exception);

        return new ResponseEntity<HttpResponse>(response, response.getStatus());
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<HttpResponse> handleAuthenticationException(AuthenticationException exception) {
        log.error(exception.getLocalizedMessage());

        final HttpResponse response = this.createHttpResponse(HttpStatus.UNAUTHORIZED, exception);

        return new ResponseEntity<HttpResponse>(response, response.getStatus());
    }
    
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<HttpResponse> handleException(Exception exception) {
        log.error(exception.getLocalizedMessage());

        final HttpResponse response = this.createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        
        return new ResponseEntity<HttpResponse>(response, response.getStatus());
    }

    private HttpResponse createHttpResponse(
        final HttpStatus httpStatus, 
        final Exception exception
    ) {
        return HttpResponse.of(
            httpStatus.value(), 
            httpStatus, 
            httpStatus.getReasonPhrase(), 
            exception.getLocalizedMessage()
        );
    }
}
