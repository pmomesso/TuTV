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

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private Validator validator;
    @Autowired
    private UserService userService;


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
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response login(@Valid LoginDTO loginDto) {

        Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDto);
        if(!violations.isEmpty()) {
            return status(Status.BAD_REQUEST).build();
        }
        User loggedInUser;
        if(loginDto.getConfirmationKey() != null){
            Optional<User> user = userService.activateUser(loginDto.getConfirmationKey());
            if(!user.isPresent()){
                return status(Status.NOT_FOUND).build();
            }
            loggedInUser = user.get();
        }
        else{
            Optional<User> user = userService.findByMail(loginDto.getUsername());
            if(!user.isPresent() || !passwordEncoder.matches(loginDto.getPassword(),user.get().getPassword())){
                return status(Status.BAD_REQUEST).build();
            }
            if(user.get().getConfirmationKey() != null && !user.get().getConfirmationKey().isEmpty()){
                return status(Status.UNAUTHORIZED).build();
            }
            loggedInUser = user.get();
        }
        String token = jwtUtil.generateToken(loggedInUser);
        return ok().header("Authorization","Bearer " + token).entity(new UserDTO(loggedInUser)).build();
    }
}
