package org.jwt.oauth.AuthenticationServer.model;

/**
 * Represents available roles for access control.
 */
public enum UserRoles
{
    /**
     * Admin privileges.
     */
    ROLE_ADMIN,

    /**
     * Basic user privileges.
     */
    ROLE_NORMALUSER,

    /**
     * Extended privileges, e.g., premium features.
     */
    ROLE_PREMIUMUSER
}
