package org.jwt.oauth.AuthenticationServer.controller;

import org.jwt.oauth.AuthenticationServer.constants.ExceptionConstants;
import org.jwt.oauth.AuthenticationServer.dto.ResponseMessage;
import org.jwt.oauth.AuthenticationServer.exception.UserAlreadyExistsException;
import org.jwt.oauth.AuthenticationServer.exception.UserNotFoundException;
import org.jwt.oauth.AuthenticationServer.model.User;
import org.jwt.oauth.AuthenticationServer.service.UserServiceImpl;
import org.jwt.oauth.AuthenticationServer.utilies.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * Public APIs (no authentication required).
 */
@RestController
@RequestMapping("/api/v1/public/user")
public class PublicUserController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     * Registers new user.
     * @param user
     * @return
     */
    @PostMapping("/signup") // add user
    public ResponseEntity<?> signUp(@RequestBody User user)
    {
        try {
            return new ResponseEntity<>(new ResponseMessage(
                    null,userService.addUser(user), ExceptionConstants.USERS_FOUND, HttpStatus.OK
            ), HttpStatus.OK);
        }
        catch (Exception exception)
        {
            if(exception.getClass().equals(UserAlreadyExistsException.class))
            {
                return new ResponseEntity<>(new ResponseMessage(
                        null,null, ExceptionConstants.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST
                ), HttpStatus.BAD_REQUEST);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Authenticates user & returns JWT token + user info.
     * @param user
     * @return
     */
    @PostMapping("/auth/login") //
    public ResponseEntity<?> login(@RequestBody User user)
    {
        try {

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),
                    user.getPassWord()));
            String jwtToken = jwtUtil.generateToken(userDetails);
            return new ResponseEntity<>(new ResponseMessage(
                    jwtToken,null,userService.getUserByName(userDetails.getUsername()), ExceptionConstants.USERS_FOUND, HttpStatus.OK
            ), HttpStatus.OK);
        }
        catch (UserNotFoundException ex) {
            return new ResponseEntity<>(new ResponseMessage(
                    null, null, ExceptionConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND
            ), HttpStatus.NOT_FOUND);
        }
        catch (BadCredentialsException badCredentialsException)
        {
            return new ResponseEntity<>(new ResponseMessage(
                    null,null, ExceptionConstants.BAD_CREDENTIAL, HttpStatus.BAD_REQUEST
            ), HttpStatus.BAD_REQUEST);
        }
        catch (UsernameNotFoundException userNotFoundException)
        {
            return new ResponseEntity<>(new ResponseMessage(
                    null,null, ExceptionConstants.USER_NOT_FOUND, HttpStatus.BAD_REQUEST
            ), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
