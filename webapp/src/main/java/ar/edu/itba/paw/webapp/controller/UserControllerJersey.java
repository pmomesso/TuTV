package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UsersList;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
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

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response banUser(UserDTO userDTO) {
        try {
            if(userDTO.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            userService.banUser(userDTO.getId());
            return Response.accepted().entity(new UserDTO(userService.findById(userDTO.getId()).get())).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
