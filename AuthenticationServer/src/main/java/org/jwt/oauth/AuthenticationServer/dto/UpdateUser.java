package org.jwt.oauth.AuthenticationServer.dto;



import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.jwt.oauth.AuthenticationServer.model.UserRoles;

import java.util.List;

/**
 * DTO for update request.
 */
@Data
@NoArgsConstructor
public class UpdateUser {
    /**
     * Current username. Used to fetch the user.
     */
    @NonNull
    private String userName;
    /**
     * New username (optional).
     */
    private String newUserName;
    /**
     * New password (optional).
     */
    private String passWord;
    /**
     * New email (optional).
     */
    private String userEmail;
    /**
     * Optional roles update.
     */
    private List<UserRoles> userRoles;
}
