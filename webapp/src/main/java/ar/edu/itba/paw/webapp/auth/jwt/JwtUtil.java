package ar.edu.itba.paw.webapp.auth.jwt;

import ar.edu.itba.paw.model.User;
import io.jsonwebtoken.*;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JwtUtil {

    private static final String KEY_FILE = "jwt.key";
    private static final String ISSUER = "tutv-api";
    private static final String AUDIENCE = "tutv-app";

    private String getJWTKey() {
        InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream(KEY_FILE);
        try {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    public JwtUtil() {}

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public User parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(getJWTKey())
                    .parseClaimsJws(token)
                    .getBody();

            User u = new User();
            u.setMailAddress(body.getSubject());
            u.setIsAdmin(body.get("isAdmin",Boolean.class));

            return u;

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param username the username for which the token will be generated
     * @param isAdmin if the user is admin
     * @return the JWT token
     */
    public String generateToken(String username, boolean isAdmin) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("isAdmin", isAdmin);

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setAudience(AUDIENCE)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, getJWTKey())
                .compact();
    }
}
