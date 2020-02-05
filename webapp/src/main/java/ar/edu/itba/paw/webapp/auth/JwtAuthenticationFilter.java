package ar.edu.itba.paw.webapp.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private static final String SECRET_KEY = "secret_key";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if(!request.getMethod().equalsIgnoreCase("POST")){
            return new UsernamePasswordAuthenticationToken(null, null);
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException, ServletException {
        if(request.getMethod().equalsIgnoreCase(("POST"))){
            UserDetails user = ((UserDetails) authentication.getPrincipal());

            List<String> roles = user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = Jwts.builder()
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .setHeaderParam("type", "JWT")
                    .setIssuer("bilazy-api")
                    .setAudience("bilazy-app")
                    .setSubject(user.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .claim("role", roles)
                    .compact();

            response.addHeader("Authorization", "Bearer " + token);
        }
        filterChain.doFilter(request,response);
    }
}
