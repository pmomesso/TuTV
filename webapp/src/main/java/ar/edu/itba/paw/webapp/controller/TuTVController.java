package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class TuTVController {

    @Autowired
    private ServletContext servletContext;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/")
    public Response getTuTV(@QueryParam("page") Integer page) throws FileNotFoundException {
        File index = new File( servletContext.getRealPath("WEB-INF/view/react-app/build/index.html"));
        return Response.ok(new FileInputStream(index)).build();
    }
}
