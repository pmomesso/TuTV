package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OurAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String PRIOR_URL = "URL_BEFORE_AUTH";

    public OurAuthenticationSuccessHandler(String target) {
        setDefaultTargetUrl(target);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws ServletException, IOException {
        HttpSession s = req.getSession();

        if(s == null)
            return;

        //String redirectUrl = (String) s.getAttribute(PRIOR_URL);

        SavedRequest savedRequest = (SavedRequest) s.getAttribute("SPRING_SECURITY_SAVED_REQUEST");

        if(savedRequest != null) {
            //s.removeAttribute(redirectUrl);

            getRedirectStrategy().sendRedirect(req, res, savedRequest.getRedirectUrl());
        } else {
            super.onAuthenticationSuccess(req, res, auth);
        }


    }

}
