package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.exceptionmappers.ApiExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ApiExceptionMapper.class);
    }

}
