package org.jwt.oauth.AuthenticationServer.exception;

import org.jwt.oauth.AuthenticationServer.constants.ExceptionConstants;

public class UserAlreadyExistsException extends RuntimeException{
    @Override
    public String getMessage() {
        return ExceptionConstants.USER_ALREADY_EXISTS;
    }
}
