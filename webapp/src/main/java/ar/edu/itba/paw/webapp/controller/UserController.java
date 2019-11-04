package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.either.Either;
import ar.edu.itba.paw.model.errors.Errors;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.form.UpdateUserForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SeriesService seriesService;

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
    public ModelAndView profile(@RequestParam("id") long userId,@ModelAttribute("updateUserForm") final UpdateUserForm form) throws NotFoundException, UnauthorizedException, BadRequestException {

        User u = userService.findById(userId).orElseThrow(NotFoundException::new);
        ModelAndView mav = new ModelAndView("profile");
        mav.addObject("userProfile", u);
        mav.addObject("followedSeries",seriesService.getAddedSeries(userId));
        HashMap<Genre,Integer> g = new HashMap<>();
        Genre g1 = new Genre("Soap1");
        Genre g2 = new Genre("Soap2");
        Genre g3 = new Genre("Soap3");
        Genre g4 = new Genre("Soap4");
        Genre g5 = new Genre("Soap5");
        g.put(g1,1);
        g.put(g2,2);
        g.put(g3,3);
        g.put(g4,4);
        g.put(g5,5);
//        TODO create method that returns Map<Genre,Integer> where integer is the number of series of the genre that the user follows
        mav.addObject("genreStats", g);
        mav.addObject("recentlyWatched", seriesService.getRecentlyWatchedList(6));
        mav.addObject("favoriteShows", new ArrayList<Series>());
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin(@RequestParam(required = false) String error) {
        Optional<User> user = userService.getLoggedUser();
        if(user.isPresent()){
            return new ModelAndView("redirect:/");
        }
        else{
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
    public ModelAndView register(HttpServletRequest request, @Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) throws UnauthorizedException, BadRequestException {
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
        String extension = FilenameUtils.getExtension(avatar.getOriginalFilename());
        final List<String> supportedExtensions = Arrays.asList("png","jpg","jpeg");
        if(!supportedExtensions.contains(extension)){
            throw new BadRequestException();
        }
        try {
            userService.setUserAvatar(u.getId(), avatar.getBytes());
        } catch (IOException e) {
            throw new BadRequestException();
        }

        return new ModelAndView("redirect:/profile?id="+u.getId());
    }

    @RequestMapping(value = "/mailconfirm", method = RequestMethod.GET)
    public ModelAndView showRegister(@RequestParam("token") String token) {

        boolean activated = userService.activateUser(token);

        ModelAndView mav = new ModelAndView("mailconfirm");
        mav.addObject("activated", activated);
        return mav;
    }

    @RequestMapping(value = "/user/{userId}/avatar", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public @ResponseBody
    byte[] getImageWithMediaType(@PathVariable long userId) throws IOException, NotFoundException {
        return userService.getUserAvatar(userId).orElseThrow(NotFoundException::new);
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ModelAndView updateUsername(@Valid @ModelAttribute("updateUserForm") final UpdateUserForm form, final BindingResult errors) throws UnauthorizedException, NotFoundException, BadRequestException {
        User u = userService.getLoggedUser().orElseThrow(UnauthorizedException::new);
        if(errors.hasErrors()){
            ModelAndView mav = profile(u.getId(),form);
            mav.addObject("formErrors",true);
            return mav;
        }
        boolean updated = userService.updateLoggedUserName(form.getUsername());
        if(updated){
            return new ModelAndView("redirect:/profile?id="+u.getId());
        }
        else{
            ModelAndView mav = profile(u.getId(),form);
            mav.addObject("exists",true);
            return mav;
        }
    }
}
