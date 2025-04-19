package org.jwt.oauth.AuthenticationServer.exception;

import org.jwt.oauth.AuthenticationServer.constants.ExceptionConstants;

public class UserNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return ExceptionConstants.USER_NOT_FOUND;
    }
}
