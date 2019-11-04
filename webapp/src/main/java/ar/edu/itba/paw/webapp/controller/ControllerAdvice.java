package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.ApiException;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Optional;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdvice.class);

    @Autowired
    private UserService userService;

    @ExceptionHandler({BadRequestException.class,MaxUploadSizeExceededException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public final ModelAndView badRequestHandler(Exception ex, WebRequest request){
        ApiException apiException = (ex instanceof  BadRequestException) ? (ApiException)ex : new BadRequestException();
        return setupErrorModelAndView(apiException);
    }
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public final ModelAndView unauthorizedHandler(UnauthorizedException ex, WebRequest request){
        return setupErrorModelAndView(ex);
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public final ModelAndView notFoundHandler(NotFoundException ex, WebRequest request){
        return setupErrorModelAndView(ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public final ModelAndView serverErrorHandler(Exception ex, WebRequest request) {
        final ModelAndView mav = new ModelAndView("error");
        mav.addObject("status","error.500status");
        mav.addObject("body","error.500body");
        mav.addObject("details",new ArrayList<>());
        ex.printStackTrace();
        return mav;
    }

    @ModelAttribute("isLogged")
    public boolean isLogged() { return userService.getLoggedUser().isPresent(); }

    @ModelAttribute("user")
    public User loggedUser() {
        Optional<User> loggedUser = userService.getLoggedUser();
        LOGGER.debug("Logged user is {}", loggedUser.orElse(null));
        return loggedUser.orElse(null);
    }

    private ModelAndView setupErrorModelAndView(ApiException apiException){
        final ModelAndView mav = new ModelAndView("error");
        mav.addObject("status",apiException.getStatus());
        mav.addObject("body",apiException.getBody());
        mav.addObject("details",apiException.getDetails());
        return mav;
    }
}
