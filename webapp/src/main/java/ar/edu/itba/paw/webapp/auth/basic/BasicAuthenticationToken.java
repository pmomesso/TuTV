package ar.edu.itba.paw.webapp.auth.basic;

import ar.edu.itba.paw.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class BasicAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String value;

    public BasicAuthenticationToken(String basicAuthorization){
        super(new User(),new ArrayList<>());
        this.value = basicAuthorization;
    }
    public BasicAuthenticationToken(String username, String password){
        super(username,password);
    }
    public BasicAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
    public BasicAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public String getValue(){
        return value;
    }
}
