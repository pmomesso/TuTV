package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.exceptionmappers.AccessDeniedExceptionMapper;
import ar.edu.itba.paw.webapp.exceptionmappers.ApiExceptionMapper;
import ar.edu.itba.paw.webapp.exceptionmappers.NotFoundExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ApiExceptionMapper.class);
        register(NotFoundExceptionMapper.class);
        register(AccessDeniedExceptionMapper.class)
    }

}
