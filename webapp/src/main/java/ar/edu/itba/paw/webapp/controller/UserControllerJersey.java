package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UsersList;
import ar.edu.itba.paw.model.either.Either;
import ar.edu.itba.paw.model.errors.Errors;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.auth.jwt.JwtUtil;
import ar.edu.itba.paw.webapp.dtos.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("users")
@Component
public class UserControllerJersey {


    @Context
    UriInfo uri;

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(LoginDTO loginDto) {
        Optional<User> user = userService.findByMail(loginDto.getUsername());
        if(!user.isPresent() || (user.get().getConfirmationKey() != null && !user.get().getConfirmationKey().isEmpty())){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if(!passwordEncoder.matches(loginDto.getPassword(),user.get().getPassword())){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String token = jwtUtil.generateToken(user.get());
        return Response.accepted().header("Authorization","Bearer " + token).entity(new UserDTO(user.get())).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response register(RegisterDTO register) {
        final URI baseUri = uri.getBaseUri();
        Either<User, Collection<Errors>> u;
        try{
            u = userService.createUser(register.getUsername(),register.getPassword(),register.getMail(),false,baseUri.toString());
        }catch(UnauthorizedException e){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if(u.isValuePresent()){
            return Response.accepted().entity(new UserDTO(u.getValue())).build();
        }
        else{
            String conflictMsg;
            if(u.getAlternative().contains(Errors.MAIL_ALREADY_IN_USE)){
                conflictMsg = "Mail Conflict";
            }
            else {
                conflictMsg = "Username Conflict";
            }
            return Response.status(Response.Status.CONFLICT).entity(conflictMsg).build();
        }
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mailconfirm")
    public Response confirmMail(ConfirmationDTO confirmation){
        Optional<User> user = userService.activateUser(confirmation.getConfirmationKey());
        if(!user.isPresent()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else{
            String token = jwtUtil.generateToken(user.get());
            return Response.accepted().header("Authorization","Bearer " + token).entity(new UserDTO(user.get())).build();
        }
    }
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
