package ar.edu.itba.paw.webapp.auth.basic;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.auth.AuthenticatedUser;
import ar.edu.itba.paw.webapp.auth.exceptions.UnabledUserException;
import ar.edu.itba.paw.webapp.auth.exceptions.UnmatchedPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Component
public class BasicAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public BasicAuthenticationProvider(){
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return (BasicAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        BasicAuthenticationToken basicAuthenticationToken = (BasicAuthenticationToken) authentication;
        Optional<User> user = userService.activateUser(basicAuthenticationToken.getValue());
        if(!user.isPresent()){
            String[] usernamePassword = new String(Base64.getDecoder().decode(basicAuthenticationToken.getValue())).split(":");
            if(usernamePassword.length != 2){
                throw new UnmatchedPasswordException("Invalid username or password");
            }
            user = userService.findByMail(usernamePassword[0]);
            if(!user.isPresent() || !passwordEncoder.matches(usernamePassword[1],user.get().getPassword())){
                throw new UnmatchedPasswordException("Invalid username or password");
            }
            if(user.get().getConfirmationKey() != null && !user.get().getConfirmationKey().isEmpty()){
                throw new UnabledUserException("Username is not enabled yet");
            }
        }
        User parsedUser = user.get();
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        if(parsedUser.getIsAdmin()){
            authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return new AuthenticatedUser(parsedUser.getMailAddress(),authorityList);
    }
}
