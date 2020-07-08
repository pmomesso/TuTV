package ar.edu.itba.paw.webapp.controller;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Provider
@PreMatching
public class LocaleFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if(request.getLocale() != null) {
            LocaleContextHolder.setLocale(request.getLocale());
        }
    }
}
