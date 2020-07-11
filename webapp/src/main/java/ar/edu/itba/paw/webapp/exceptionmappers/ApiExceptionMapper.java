package ar.edu.itba.paw.webapp.exceptionmappers;

import ar.edu.itba.paw.model.exceptions.ApiException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ApiExceptionMapper implements ExceptionMapper<ApiException> {
    @Override
    public Response toResponse(ApiException exception) {
        return Response.status(exception.getStatusCode()).entity("").build();
    }
}
