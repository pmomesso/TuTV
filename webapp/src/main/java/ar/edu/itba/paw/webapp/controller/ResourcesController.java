package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Path("static")
@Component
public class ResourcesController {

    @Autowired
    private ServletContext servletContext;

    @GET
    @Path("/js/{filename}")
    @Produces(value = { "text/javascript" })
    public Response getJS(@PathParam("filename") final String filename) throws FileNotFoundException {
        File resource = new File(servletContext.getRealPath("WEB-INF/view/react-app/build/static/js/"+filename));
        return Response.ok(new FileInputStream(resource)).build();
    }

    @GET
    @Path("/css/{filename}")
    @Produces(value = { "text/css" })
    public Response getCSS(@PathParam("filename") final String filename) throws FileNotFoundException {
        File resource = new File(servletContext.getRealPath("WEB-INF/view/react-app/build/static/css/"+filename));
        return Response.ok(new FileInputStream(resource)).build();
    }

    @GET
    @Path("/media/{filename}")
    @Produces(value = { "image/base64" })
    public Response getImage(@PathParam("filename") final String filename) throws FileNotFoundException {
        File resource = new File(servletContext.getRealPath("WEB-INF/view/react-app/build/static/media/"+filename));
        return Response.ok(new FileInputStream(resource)).build();
    }
}
