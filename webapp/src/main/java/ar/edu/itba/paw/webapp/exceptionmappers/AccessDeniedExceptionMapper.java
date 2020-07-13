package ar.edu.itba.paw.webapp.exceptionmappers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Component
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {
    @Override
    public Response toResponse(AccessDeniedException exception) {
        return Response.status(Response.Status.UNAUTHORIZED).entity("").type(MediaType.TEXT_PLAIN).build();
    }
}
