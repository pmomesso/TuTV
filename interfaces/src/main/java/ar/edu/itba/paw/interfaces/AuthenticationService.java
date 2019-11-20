package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

public interface AuthenticationService {
    Set<GrantedAuthority> getUserAuthorities(User u);

    void authenticate(User u, HttpServletRequest request);

    Optional<String> getLoggedUserMail();
}
