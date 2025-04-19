package org.jwt.oauth.AuthenticationServer.service;

import org.jwt.oauth.AuthenticationServer.dto.UpdateUser;
import org.jwt.oauth.AuthenticationServer.model.User;

import java.util.List;

/**
 * Declares service methods for user operations.
 */
public interface IUserService {
    /**
     * Declares service methods for user operations.
     * @param user
     * @return
     */
    User addUser(User user);

    /**
     * Fetches user by username.
     * @param userName
     * @return
     */
    User getUserByName(String userName);

    /**
     * Retrieves all users.
     * @return
     */
    List<User> getAllUser();

    /**
     * Deletes user by username.
     * @param userName
     * @return
     */
    boolean deleteUser(String userName);

    /**
     * Updates user details.
     * @param user
     * @return
     */
    User updateUser(UpdateUser user);
}
