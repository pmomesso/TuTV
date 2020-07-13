package ar.edu.itba.paw.webapp.exceptionmappers;

import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

//mapper for javax.ws.rs notfoundexception
@Component
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity("").type(MediaType.TEXT_PLAIN).build();
    }
}
