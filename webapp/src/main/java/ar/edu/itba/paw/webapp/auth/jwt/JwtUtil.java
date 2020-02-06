package ar.edu.itba.paw.webapp.auth.jwt;

import ar.edu.itba.paw.webapp.auth.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUtil {

    private static final String KEY_FILE = "jwt.key";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
    private static final String ISSUER = "paw-api";
    private static final String AUDIENCE = "paw-app";
    private static final String ROLE = "role";

    public JwtUtil() {}

    public String generateToken(UserDetails user, List<String> roles) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, getSecretKey())
                .setHeaderParam("type", "JWT")
                .setIssuer(ISSUER)
                .setAudience(AUDIENCE)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim(ROLE, roles)
                .compact();
    }
    private Jws<Claims> getTokenClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token);
    }
    public String getTokenUsername(String token) {
        Jws<Claims> parsedToken = getTokenClaims(token);
        return parsedToken
                .getBody()
                .getSubject();
    }
    public List<SimpleGrantedAuthority> getTokenAuthorities(String token) {
        Jws<Claims> parsedToken = getTokenClaims(token);
        return  ((List<?>) parsedToken.getBody()
                .get(ROLE)).stream()
                .map(authority -> new SimpleGrantedAuthority((String) authority))
                .collect(Collectors.toList());
    }
    private String getSecretKey() {
        InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream(KEY_FILE);
        try {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
