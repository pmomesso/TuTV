package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserService us;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        final User user = us.findByMail(mail).orElseThrow(() -> { return new UsernameNotFoundException("No such user"); });

        final Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        boolean isUserEnabled = (user.getConfirmationKey() == null || user.getConfirmationKey().isEmpty()); //TODO AGREGAR SI ESTA BANEADO

        return new UserDetails(user.getMailAddress(), user.getPassword(), isUserEnabled, authorities);
    }
}