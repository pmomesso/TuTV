package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.dtos.SampleEchoDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("echo")
public class EchoControllerJersey {

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String message() {
        return "Send a string and I will echo it!";
    }

    @POST
    @Path("/")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public SampleEchoDTO echo(String message) {
        return new SampleEchoDTO(message);
    }
}
