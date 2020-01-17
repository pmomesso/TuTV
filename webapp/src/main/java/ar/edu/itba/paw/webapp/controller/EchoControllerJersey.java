package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.dtos.SampleEchoDTO;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    @Produces(MediaType.TEXT_PLAIN)
    public SampleEchoDTO echo(final SampleEchoDTO message) {
        return new SampleEchoDTO(message.getMessage());
    }
}
