package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UsersList;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.dtos.UserDTO;
import ar.edu.itba.paw.webapp.dtos.SeriesListsDTO;
import ar.edu.itba.paw.webapp.dtos.UsersListDTO;

import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("users")
public class UserControllerJersey {

    @Autowired
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}")
    public Response getUserById(@PathParam("userId") Long userId) {
        //Should be only for admins
        Optional<User> optLoggedUser = userService.getLoggedUser();
        if(!optLoggedUser.isPresent() || !optLoggedUser.get().getIsAdmin()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Optional<User> optUser = userService.findById(userId);
        if(!optUser.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new UserDTO(optUser.get())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getUsersList(@QueryParam("page") Integer page) {
        //Should be only for admins
        Optional<User> optLoggedUser = userService.getLoggedUser();
        if(!optLoggedUser.isPresent() || !optLoggedUser.get().getIsAdmin()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        UsersList usersList = null;
        try {
            usersList = userService.getAllUsersExceptLoggedOne(page);
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(new UsersListDTO(usersList)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/lists")
    public Response getUserFavourites(@PathParam("userId") Long userId) {
        Optional<User> optUser = userService.findById(userId);
        if(!optUser.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        User user = optUser.get();
        return Response.ok(new SeriesListsDTO(user.getLists().stream().collect(Collectors.toList()))).build();
    }

}
