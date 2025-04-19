package org.jwt.oauth.AuthenticationServer.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.jwt.oauth.AuthenticationServer.constants.ExceptionConstants;
import org.jwt.oauth.AuthenticationServer.dto.ResponseMessage;
import org.jwt.oauth.AuthenticationServer.exception.NoUserFoundException;
import org.jwt.oauth.AuthenticationServer.exception.UserAlreadyExistsException;
import org.jwt.oauth.AuthenticationServer.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Handles common exceptions globally with @ControllerAdvice.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionhandler {
    /**
     * 400 - Invalid login credentials.
     * @param badCredentialsException
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseMessage> badCredential(BadCredentialsException badCredentialsException)
    {
        log.error(ExceptionConstants.BAD_CREDENTIAL);
        return new ResponseEntity<>(new ResponseMessage
                (null,null,ExceptionConstants.BAD_CREDENTIAL,HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    /**
     * 404 - When user not found.
     * @param noUserFoundException
     * @return
     */
    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<ResponseMessage> noUserFound(NoUserFoundException noUserFoundException)
    {
        log.error(ExceptionConstants.NO_USER_FOUND);
        return new ResponseEntity<>(new ResponseMessage(null,null,ExceptionConstants.NO_USER_FOUND,
                HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
    }

    /**
     * 404 - When user not found.
     * @param userFoundException
     * @return
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseMessage> userNotFound(UserNotFoundException userFoundException)
    {
        log.error(ExceptionConstants.USER_NOT_FOUND);
        return new ResponseEntity<>(new ResponseMessage(null,null,ExceptionConstants.USER_NOT_FOUND,
                HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
    }

    /**
     * 400 - User already exists on signup.
     * @param userAlreadyExistsException
     * @return
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseMessage> userAlreadyExists(UserAlreadyExistsException userAlreadyExistsException)
    {
        log.error(ExceptionConstants.USER_ALREADY_EXISTS);
        return new ResponseEntity<>(new ResponseMessage(null,null,ExceptionConstants.USER_ALREADY_EXISTS,
                HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
    }

    /**
     * 403 - Access denied.
     * @param forbidden
     * @return
     */
    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<ResponseMessage> forbidden(HttpClientErrorException.Forbidden forbidden)
    {
        log.error(ExceptionConstants.FORBIDDEN);
        return new ResponseEntity<>(new ResponseMessage(null,null,ExceptionConstants.FORBIDDEN,
                HttpStatus.FORBIDDEN),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResponseMessage> NoEndpointFound(NoHandlerFoundException noHandlerFoundException)
    {
        log.error(String.valueOf(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(new ResponseMessage(null,null,ExceptionConstants.PAGE_NOT_FOUND,
                HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
    }
}
