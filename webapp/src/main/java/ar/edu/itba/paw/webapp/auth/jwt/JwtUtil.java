package ar.edu.itba.paw.webapp.auth.jwt;

import ar.edu.itba.paw.model.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtUtil {

    private static final String KEY_FILE = "jwt.key";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
    private static final String ISSUER = "paw-api";
    private static final String AUDIENCE = "paw-app";
    private static final String ROLE = "role";

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
                    .setSigningKey("secret_key")
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
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(User u) {
        Claims claims = Jwts.claims().setSubject(u.getMailAddress());
        claims.put("isAdmin", u.getIsAdmin());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "secret_key")
                .compact();
    }
}
