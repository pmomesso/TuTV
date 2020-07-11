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

import static javax.ws.rs.core.Response.*;

@Path("static")
@Component
public class ResourcesControllerJersey {

    @Autowired
    private ServletContext servletContext;

    @GET
    @Produces(value = {"text/javascript"})
    @Path("/js/{filename}")
    public Response getJs(@PathParam("filename") String filename) throws FileNotFoundException {
        File resource = new File(servletContext.getRealPath("WEB-INF/view/react-app/build/static/js/" + filename));
        if(!resource.exists()) {
            return status(Status.NOT_FOUND).build();
        }
        return ok(new FileInputStream(resource)).build();
    }

    @GET
    @Produces(value = {"text/css"})
    @Path("/css/{filename}")
    public Response getCss(@PathParam("filename") String filename) throws FileNotFoundException {
        File resource = new File(servletContext.getRealPath("WEB-INF/view/react-app/build/static/css/" + filename));
        if(!resource.exists()) {
            return status(Status.NOT_FOUND).build();
        }
        return ok(new FileInputStream(resource)).build();
    }

    @GET
    @Produces(value = {"image/base64"})
    @Path("/media/{filename}")
    public Response getMedia(@PathParam("filename") String filename) throws FileNotFoundException {
        File resource = new File(servletContext.getRealPath("WEB-INF/view/react-app/build/static/media/" + filename));
        if(!resource.exists()) {
            return status(Status.NOT_FOUND).build();
        }
        return ok(new FileInputStream(resource)).build();
    }
}
