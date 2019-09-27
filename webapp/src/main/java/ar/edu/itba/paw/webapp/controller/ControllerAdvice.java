package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

// atributos que quiero entre todos los controllers.. que se agregue a cualquier model and view ese atributo

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @Autowired
    private UserService userService;

//    NO MOSTRAR STACK TRACE AL USUARIO
//    @ExceptionHandler
//    public ModelAndView internalError() {
//        // TODO log it
//        return new ModelAndView("error");
//    }

//    @ExceptionHandler(UserNotFoundException.class)
//    public ModelAndView userNotFound() {
//        // TODO log it
//        return new ModelAndView("missing-user");
//    }

    @ModelAttribute("user")
    public User loggedUser() {
        return userService.getLoggedUser();
    }
}
