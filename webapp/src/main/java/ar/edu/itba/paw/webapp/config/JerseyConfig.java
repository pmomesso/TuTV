package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.exceptionmappers.*;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ApiExceptionMapper.class);
        register(NotFoundExceptionMapper.class);
        register(AccessDeniedExceptionMapper.class);
    }

}
