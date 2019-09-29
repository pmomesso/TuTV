package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        setDefaultFailureUrl("/login?error=true");

        super.onAuthenticationFailure(request,response,exception);

        if (exception.getMessage().equalsIgnoreCase("User account has expired")){
            request.getSession().setAttribute("userExpired",true);
            request.getSession().setAttribute("UserNotConfirmError",false);
        } else if(exception.getMessage().equalsIgnoreCase("User is disabled")){
            request.getSession().setAttribute("UserNotConfirmError",true);
            request.getSession().setAttribute("userExpired",false);
        } else {
            request.getSession().setAttribute("UserNotConfirmError",false);
            request.getSession().setAttribute("userExpired",false);
        }

    }

}
