package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.auth.jwt.JwtUtil;
import ar.edu.itba.paw.webapp.dtos.LoginDTO;
import ar.edu.itba.paw.webapp.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Set;

import static javax.ws.rs.core.Response.*;

@Path("/")
@Component
public class TuTVController {

    @Autowired
    private ServletContext servletContext;


    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/")
    public Response getTuTV(@QueryParam("page") Integer page) throws FileNotFoundException {
        File index = new File(servletContext.getRealPath("WEB-INF/view/react-app/build/index.html"));
        if(!index.exists()) {
            return status(Status.NOT_FOUND).build();
        }
        return ok(new FileInputStream(index)).build();
    }
}
