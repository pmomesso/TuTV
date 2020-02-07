package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
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
        try {
            Optional<User> user = userService.getLoggedUser();
            if(!user.isPresent() || !user.get().getIsAdmin()) throw new UnauthorizedException();
            return Response.ok(new UserDTO(userService.findById(userId).orElseThrow(NotFoundException::new))).build();
        } catch(NotFoundException | UnauthorizedException e) {
            if(e instanceof NotFoundException)
                return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
            else
                return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getUsersList(@QueryParam("page") Integer page) {
        //Should be only for admins
        try {
            Optional<User> user = userService.getLoggedUser();
            if(!user.isPresent() || !user.get().getIsAdmin()) throw new UnauthorizedException();
            return Response.ok(new UsersListDTO(userService.getAllUsersExceptLoggedOne(page))).build();
        } catch(NotFoundException | UnauthorizedException e) {
            if(e instanceof NotFoundException)
                return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
            else
                return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/lists")
    public Response getUserFavourites(@PathParam("userId") Long userId) {
        try {
            Optional<User> user = userService.findById(userId);
            if(!user.isPresent()) throw new NotFoundException();
            return Response.ok(new SeriesListsDTO(user.get().getLists().stream().collect(Collectors.toList()))).build();
        } catch(NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
