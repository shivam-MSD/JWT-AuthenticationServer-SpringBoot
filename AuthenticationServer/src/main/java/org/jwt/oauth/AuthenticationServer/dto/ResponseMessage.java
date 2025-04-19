package org.jwt.oauth.AuthenticationServer.dto;

import lombok.*;
import org.jwt.oauth.AuthenticationServer.model.User;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Standardized response wrapper for API responses.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseMessage {
    /**
     * JWT token (only during login).
     */
    private String jwtToken;
    /**
     * List of users (used in /all).
     */
    private List<User> users;
    /**
     * Single user (used in /user).
     */
    private User user;
    /**
     * Message string
     */
    private String message;
    /**
     * HTTP status (e.g., 200 OK).
     */
    private HttpStatus httpStatus;

    public ResponseMessage(List<User> users, User user, String message, HttpStatus httpStatus) {
        this.users = users;
        this.user = user;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}