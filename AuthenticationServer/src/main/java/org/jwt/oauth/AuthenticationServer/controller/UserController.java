package org.jwt.oauth.AuthenticationServer.controller;

import org.jwt.oauth.AuthenticationServer.constants.ExceptionConstants;
import org.jwt.oauth.AuthenticationServer.dto.ResponseMessage;
import org.jwt.oauth.AuthenticationServer.dto.UpdateUser;
import org.jwt.oauth.AuthenticationServer.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Secured endpoints accessible only after login (JWT protected).
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    /**
     * Admin-only. Gets all users.
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers()
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return new ResponseEntity<>(new ResponseMessage(
                    userService.getAllUser(),null, ExceptionConstants.USERS_FOUND,HttpStatus.OK
            ), HttpStatus.OK);
        }
        catch (HttpClientErrorException.Forbidden forbidden)
        {
            return new ResponseEntity<>(new ResponseMessage(
                    null,null, ExceptionConstants.ACCESS_DENIED,HttpStatus.FORBIDDEN
            ), HttpStatus.FORBIDDEN);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets current user (from JWT).
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getUser()
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return new ResponseEntity<>(new ResponseMessage(
                    null,userService.getUserByName(authentication.getName()), ExceptionConstants.USERS_FOUND,HttpStatus.OK
            ), HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates logged-in user details
     * @param user
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUser user)
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return new ResponseEntity<>(new ResponseMessage(
                    null,userService.updateUser(user), ExceptionConstants.USERS_FOUND,HttpStatus.OK
            ), HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete User Account
     * @param user
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody UpdateUser user)
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userService.deleteUser(user.getUserName());
            return new ResponseEntity<>(new ResponseMessage(
                    null,null,ExceptionConstants.USER_DELETED,
                    HttpStatus.OK),
                    HttpStatus.OK
            );
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
