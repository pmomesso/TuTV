package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.auth.jwt.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class OurAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private JwtUtil jwtUtil = new JwtUtil();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws ServletException, IOException {
        boolean isAdmin = false;
        for(GrantedAuthority a : auth.getAuthorities()){
            if(a.getAuthority().equals("ROLE_ADMIN")){
                isAdmin = true;
                break;
            }
        }
        res.addHeader("Authorization","Bearer " + jwtUtil.generateToken(auth.getName(),isAdmin));
    }

}
