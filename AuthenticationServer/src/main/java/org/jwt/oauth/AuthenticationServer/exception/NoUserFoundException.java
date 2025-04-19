package org.jwt.oauth.AuthenticationServer.exception;

import org.jwt.oauth.AuthenticationServer.constants.ExceptionConstants;

public class NoUserFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return ExceptionConstants.NO_USER_FOUND;
    }
}
