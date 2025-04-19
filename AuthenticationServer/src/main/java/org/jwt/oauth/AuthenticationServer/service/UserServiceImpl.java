package org.jwt.oauth.AuthenticationServer.service;

import org.jwt.oauth.AuthenticationServer.dto.UpdateUser;
import org.jwt.oauth.AuthenticationServer.exception.NoUserFoundException;
import org.jwt.oauth.AuthenticationServer.exception.UserAlreadyExistsException;
import org.jwt.oauth.AuthenticationServer.exception.UserNotFoundException;
import org.jwt.oauth.AuthenticationServer.model.User;
import org.jwt.oauth.AuthenticationServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implements the user logic and interacts with the repository.
 */
@Service
public class UserServiceImpl implements IUserService {
    /**
     * MongoDB interaction.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Encrypts user passwords with BCrypt.
     */
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Checks if user exists; encrypts password; saves user.
     * @param user
     * @return
     */
    @Override
    public User addUser(User user) {

        if (userRepository.findByUserName(user.getUserName()) != null)
            throw new UserAlreadyExistsException();
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        return userRepository.save(user);
    }

    /**
     * Retrieves user or throws UserNotFoundException.
     * @param userName
     * @return
     */
    @Override
    public User getUserByName(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user != null)
            return user;
        throw new UserNotFoundException();
    }

    /**
     * Gets all users or throws NoUserFoundException.
     * @return
     */
    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        if (users != null)
            return users;
        throw new NoUserFoundException();
    }

    /**
     * Deletes a user after validating existence.
     * @param userName
     * @return
     */
    @Override
    public boolean deleteUser(String userName) {
        User user = getUserByName(userName);
        userRepository.delete(user);
        return true;
    }

    /**
     * Updates username, email, or password conditionally. Checks for uniqueness.
     * @param user
     * @return
     */
    @Override
    public User updateUser(UpdateUser user) {
        Set<User> users = new HashSet<>(userRepository.findAll());
        Optional<User> fetchUserOpt = users.stream()
                .filter(u -> u.getUserName().equals(user.getUserName()))
                .findFirst();

        if (fetchUserOpt.isEmpty()) {
            throw new UserNotFoundException();
        }

        User fetchUser = fetchUserOpt.get();

        if (user.getNewUserName()!= null && user.getNewUserName().trim() != ""  && !fetchUser.getUserName().equals(user.getNewUserName())) {
            if(users.stream().anyMatch(u -> u.getUserName().equals(user.getNewUserName())))
                throw new UserAlreadyExistsException();
            fetchUser.setUserName(user.getNewUserName());
        }

        if (user.getUserEmail() != null && user.getUserEmail().trim() != "") {
            fetchUser.setUserEmail(user.getUserEmail());
        }

        if (user.getPassWord() != null && user.getPassWord().trim() != "") {
            fetchUser.setPassWord(passwordEncoder.encode(user.getPassWord()));
        }

        if (user.getUserRoles() != null && !user.getUserRoles().isEmpty()) {
            fetchUser.setUserRoles(user.getUserRoles());
        }
        return userRepository.save(fetchUser);
    }
}
