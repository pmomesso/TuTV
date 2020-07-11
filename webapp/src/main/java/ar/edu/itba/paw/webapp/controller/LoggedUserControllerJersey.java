package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

@Component
@Path("/api/user")
public class LoggedUserControllerJersey {

    @Autowired
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getLoggedInUser(){
        Optional<User> optUser = userService.getLoggedUser();
        if(!optUser.isPresent()) {
            return status(Response.Status.NOT_FOUND).build();
        }
        UserDTO userDTO = new UserDTO(optUser.get());
        return ok(userDTO).build();
    }


}
