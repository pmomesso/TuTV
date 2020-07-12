package ar.edu.itba.paw.webapp.exceptionmappers;

import org.springframework.security.access.AccessDeniedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {
    @Override
    public Response toResponse(AccessDeniedException exception) {
        return Response.status(Response.Status.UNAUTHORIZED).entity("").build();
    }
}
