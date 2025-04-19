package org.jwt.oauth.AuthenticationServer.utilies;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Handles JWT token creation and validation.
 */
@Component
public class JWTUtil {
    /**
     * Secret key for signing the token. Injected from application.properties.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Token validity period (10 hours).
     */
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    /**
     * Creates JWT with username and expiry.
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Parses and extracts the subject (username) from token.
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Checks username match and token expiry.
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Checks token expiration time.
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
