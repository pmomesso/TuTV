package ar.edu.itba.paw.webapp.auth.jwt;

import ar.edu.itba.paw.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String token;

    public JwtAuthenticationToken(String token){
        super(new User(),new ArrayList<>());
        this.token = token;
    }
    public JwtAuthenticationToken(String username, String password){
        super(username,password);
    }
    public JwtAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public String getToken(){
        return token;
    }
}
