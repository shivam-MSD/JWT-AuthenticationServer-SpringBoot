package org.jwt.oauth.AuthenticationServer.repository;

import org.bson.types.ObjectId;
import org.jwt.oauth.AuthenticationServer.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDB repository for User CRUD operations.
 */
@Repository
public interface UserRepository extends MongoRepository<User,ObjectId> {
    /**
     * Finds a user by username. Used for login & lookups.
     * @param userName
     * @return
     */
    User findByUserName(String userName);
}
