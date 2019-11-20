package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AuthenticationService;
import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.either.Either;
import ar.edu.itba.paw.model.errors.Errors;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/banUser", method = RequestMethod.POST)
    public ModelAndView banUser(@RequestParam(value = "seriesId", required = false) Long seriesId, @RequestParam("userId") long userId) throws UnauthorizedException, NotFoundException {
        userService.banUser(userId);
        if (seriesId == null)
            return new ModelAndView("redirect:/users");
        return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/unbanUser", method = RequestMethod.POST)
    public ModelAndView unbanUser(@RequestParam(value = "seriesId", required = false) Long seriesId, @RequestParam("userId") long userId) throws UnauthorizedException, NotFoundException {
        userService.unbanUser(userId);
        if (seriesId == null)
            return new ModelAndView("redirect:/users");
        return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView users(@RequestParam(value = "page", required = false) Integer page) throws UnauthorizedException {
        ModelAndView mav = new ModelAndView("users");
        Integer page_to_show = 1;
        if (page != null) {
            page_to_show = page;
        }
        mav.addObject("page", page_to_show);
        mav.addObject("users", userService.getAllUsersExceptLoggedOne(page_to_show));
        return mav;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(@RequestParam("id") long userId, @ModelAttribute("listForm") final ListForm listForm, @ModelAttribute("listModifyForm") final ListForm listModifyForm, @ModelAttribute("updateUserForm") final UpdateUserForm form) throws NotFoundException, UnauthorizedException, BadRequestException {

        User u = userService.findById(userId).orElseThrow(NotFoundException::new);
        ModelAndView mav = new ModelAndView("profile");
        mav.addObject("listForm", listForm);
        mav.addObject("listModifyForm", listModifyForm);
        mav.addObject("updateUserForm", form);
        mav.addObject("userProfile", u);
        mav.addObject("followedSeries",seriesService.getAddedSeries(userId));
        mav.addObject("genreStats", userService.getGenresStats());
        mav.addObject("recentlyWatched", seriesService.getRecentlyWatchedList(6));
        return mav;
    }

    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public ModelAndView addList(@Valid @ModelAttribute("listForm") final ListForm form, final BindingResult errors) throws Exception {
        if (errors.hasErrors()) {
            return profile(form.getUserId(), form, new ListForm(), new UpdateUserForm());
        }
        seriesService.addList(form.getName(), form.getSeriesId());
        return new ModelAndView("redirect:/profile?id=" + form.getUserId());
    }

    @RequestMapping(value = "/modifyList", method = RequestMethod.POST)
    public ModelAndView modifyList(@Valid @ModelAttribute("listModifyForm") final ListForm form, final BindingResult errors) throws Exception {
        if (errors.hasErrors()) {
            return profile(form.getUserId(), new ListForm(), form, new UpdateUserForm());
        }
        seriesService.modifyList(form.getId(), form.getName(), form.getSeriesId());
        return new ModelAndView("redirect:/profile?id=" + form.getUserId());
    }

    @RequestMapping(value = "/removeList", method = RequestMethod.POST)
    public ModelAndView deleteList(@RequestParam long id, @RequestParam long userId) throws Exception {
        seriesService.removeList(id);
        return new ModelAndView("redirect:/profile?id=" + userId);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, Model model) {
        Optional<User> user = userService.getLoggedUser();
        if(user.isPresent()) {
            return new ModelAndView("redirect:/");
        } else {
            //String referrer = request.getHeader("Referer");
            //request.getSession().setAttribute("URL_BEFORE_AUTH", referrer);
            return new ModelAndView("login");
        }
    }

    @RequestMapping(value = "/registrationsuccess", method = RequestMethod.GET)
    public ModelAndView showRegistrationSuccess() {
        return new ModelAndView("registrationsuccess");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showRegister(@ModelAttribute("registerForm") final UserForm form) {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) throws UnauthorizedException, BadRequestException {
        if (errors.hasErrors()) {
            return showRegister(form);
        }
        else if(!form.getPassword().equals(form.getRepeatPassword())){
            throw new BadRequestException();
        }
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        Either<User, Collection<Errors>> e = userService.createUser(form.getUsername(), form.getPassword(), form.getMail(),false, baseUrl);
        ModelAndView mav;

        if(!e.isValuePresent()){
            mav = showRegister(form);
            if(e.getAlternative().contains(Errors.USERNAME_ALREADY_IN_USE))
                mav.addObject("usernameExists",true);
            if(e.getAlternative().contains(Errors.MAIL_ALREADY_IN_USE))
                mav.addObject("mailExists",true);
        }
        else{
            mav = new ModelAndView("redirect:/registrationsuccess");
        }
        return mav;
    }

    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    public ModelAndView uploadAvatar(@RequestParam("avatar") MultipartFile avatar) throws UnauthorizedException, BadRequestException {
        User u = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        userService.setUserAvatar(u.getId(), avatar);

        return new ModelAndView("redirect:/profile?id="+u.getId());
    }

    @RequestMapping(value = "/mailconfirm", method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, Model model, @RequestParam("token") String token) {

        Optional<User> user = userService.activateUser(token);

        boolean activated = user.isPresent();

        if(activated)
            authenticationService.authenticate(user.get(), request);

        ModelAndView mav = new ModelAndView("mailconfirm");
        mav.addObject("activated", activated);
        return mav;
    }

    @RequestMapping(value = "/user/{userId}/avatar", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public @ResponseBody
    byte[] getImageWithMediaType(@PathVariable long userId) throws NotFoundException {
        return userService.getUserAvatar(userId).orElseThrow(NotFoundException::new);
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ModelAndView updateUsername(@Valid @ModelAttribute("updateUserForm") final UpdateUserForm form, final BindingResult errors) throws UnauthorizedException, NotFoundException, BadRequestException {
        User u = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        if(errors.hasErrors()){
            ModelAndView mav = profile(u.getId(), new ListForm(), new ListForm(), form);
            mav.addObject("formErrors",true);
            return mav;
        }
        boolean updated = userService.updateLoggedUserName(form.getUsername());
        if(updated){
            return new ModelAndView("redirect:/profile?id="+u.getId());
        }
        else{
            ModelAndView mav = profile(u.getId(), new ListForm(), new ListForm(), form);
            mav.addObject("exists",true);
            return mav;
        }
    }

    @RequestMapping(value = "/seeNotification", method = RequestMethod.GET)
    public ModelAndView seeNotification(@RequestParam("notificationId") long notificationId, @RequestParam("seriesId") long seriesId) throws NotFoundException{
        userService.setNotificationViewed(notificationId);
        return new ModelAndView("redirect:/series?id=" + seriesId);
    }
}
