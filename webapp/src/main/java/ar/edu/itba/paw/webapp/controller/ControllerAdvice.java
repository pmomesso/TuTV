package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

// atributos que quiero entre todos los controllers.. que se agregue a cualquier model and view ese atributo

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @Autowired
    private UserService userService;

    //Handler para excepcion generica.
    @ExceptionHandler(Exception.class)
    public final ModelAndView globalExceptionHandler(Exception ex, WebRequest request) {
        final ModelAndView mav = new ModelAndView("error");
        mav.addObject("status","error.500status");
        mav.addObject("body","error.500body");
        return mav;
    }

    @ModelAttribute("isLogged")
    public boolean isLogged() { return userService.getLoggedUser() != null; }

    @ModelAttribute("user")
    public User loggedUser() {
        return userService.getLoggedUser();
    }
}
