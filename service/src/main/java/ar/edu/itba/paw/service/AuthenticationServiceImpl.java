package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.AuthenticationService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Set<GrantedAuthority> getUserAuthorities(User u) {
        final Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(u.getIsAdmin())
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return authorities;
    }

    @Override
    public void authenticate(User u, HttpServletRequest request) {
        request.getSession();

        final Set<GrantedAuthority> authorities = getUserAuthorities(u);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(u, null, authorities);

        Authentication auth = authenticationManager.authenticate(token);

        token.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    public Optional<String> getLoggedUserMail() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserMail = authentication.getName();
            Optional<String> ret = Optional.of(currentUserMail);
            return ret;
        }
        return Optional.empty();
    }
}
