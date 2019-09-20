package ar.edu.itba.paw.webapp.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

// atributos que quiero entre todos los controllers.. que se agregue a cualquier model and view ese atributo

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

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

    @ModelAttribute("userId")
    public Integer loggedUserId(final HttpSession session) {
        return (Integer) session.getAttribute("logged_user");
    }
}
