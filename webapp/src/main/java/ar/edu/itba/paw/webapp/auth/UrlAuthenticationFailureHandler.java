package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        setDefaultFailureUrl("/login?error=true");

        super.onAuthenticationFailure(request,response,exception);

        HttpSession session = request.getSession();

        session.setAttribute("ErrorNotConfirmed",false);
        session.setAttribute("ErrorInvalidCredentials",false);
        session.setAttribute("ErrorExpired",false);
        session.setAttribute("ErrorOther",false);

        if(exception instanceof BadCredentialsException)
            session.setAttribute("ErrorInvalidCredentials",true);
        else if(exception instanceof DisabledException)
            session.setAttribute("ErrorNotConfirmed",true);
        else if(exception instanceof CredentialsExpiredException)
            session.setAttribute("ErrorExpired",true);
        else
            session.setAttribute("ErrorOther",true);

    }

}
