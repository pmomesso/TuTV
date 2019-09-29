package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetails extends org.springframework.security.core.userdetails.User {

    public UserDetails(String username, String password, boolean isEnabled, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, isEnabled, true, true, true, authorities);
    }
}
