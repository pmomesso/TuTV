package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.ApiException;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Optional;

// atributos que quiero entre todos los controllers.. que se agregue a cualquier model and view ese atributo

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @Autowired
    private UserService userService;

    @ExceptionHandler(Exception.class)
    public final ModelAndView globalExceptionHandler(Exception ex, WebRequest request) {
        final ModelAndView mav = new ModelAndView("error");
        if(ex instanceof ApiException){
            ApiException apiException = (ApiException) ex;
            mav.addObject("status",apiException.getStatus());
            mav.addObject("body",apiException.getBody());
            mav.addObject("details",apiException.getDetails());
        }
        //Si es una excepcion no reconocida, respondo con error 500.
        else{
            mav.addObject("status","error.500status");
            mav.addObject("body","error.500body");
            mav.addObject("details",new ArrayList<>());
        }
        return mav;
    }

    @ModelAttribute("isLogged")
    public boolean isLogged() { return userService.getLoggedUser() != null; }

    @ModelAttribute("user")
    public User loggedUser() {
        Optional<User> loggedUser = userService.getLoggedUser();
        return loggedUser.isPresent() ? loggedUser.get() : null;
    }
}
