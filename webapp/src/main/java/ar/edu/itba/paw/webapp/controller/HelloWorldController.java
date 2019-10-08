package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.either.Either;
import ar.edu.itba.paw.model.errors.Errors;
import ar.edu.itba.paw.model.exceptions.BadRequestException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.form.*;
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
public class HelloWorldController {

	@Autowired
	private UserService userService;

	@Autowired
	private SeriesService seriesService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("newShows", seriesService.getNewestSeries(0,4));
		mav.addObject("seriesMap", seriesService.getSeriesByGenreMap(0,7));
		return mav;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final ModelAndView mav = new ModelAndView("search");
		mav.addObject("genres",seriesService.getAllGenres());
		return mav;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView search(@Valid @ModelAttribute("searchForm") final SearchForm form, final BindingResult errors) {
	    if(errors.hasErrors()){
	        return search();
        }
        final ModelAndView mav = new ModelAndView("searchResults");
        mav.addObject("searchResults",seriesService.searchSeries(form.getName(),form.getGenre(),form.getNetwork(),form.getMin(),form.getMax()));
        return mav;
    }

	@RequestMapping(value = "/series", method = RequestMethod.GET)
	public ModelAndView series(@ModelAttribute("postForm") final PostForm postForm, @ModelAttribute("commentForm") final CommentForm commentForm, @RequestParam("id") long id) throws Exception {
		final ModelAndView mav = new ModelAndView("series");
		mav.addObject("series", seriesService.getSerieById(id));
		Optional<User> u = userService.getLoggedUser();
		u.ifPresent(user -> mav.addObject("hasAvatar", userService.getUserAvatar(user.getId()).isPresent()));
		mav.addObject("postForm", postForm);
		mav.addObject("commentForm", commentForm);
		return mav;
	}

	@RequestMapping(value = "/addSeries", method = RequestMethod.POST)
    public ModelAndView addSeries(@RequestParam("seriesId") long seriesId) throws NotFoundException, UnauthorizedException {
	    seriesService.followSeries(seriesId);
	    return new ModelAndView("redirect:/series?id=" + seriesId);
    }

	@RequestMapping(value = "/viewSeason", method = RequestMethod.POST)
	public ModelAndView viewSeason(@RequestParam("seriesId") long seriesId, @RequestParam("seasonId") long seasonId) throws UnauthorizedException, NotFoundException {
		seriesService.setViewedSeason(seasonId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
	}

	@RequestMapping(value = "/unviewSeason", method = RequestMethod.POST)
	public ModelAndView unviewSeason(@RequestParam("seriesId") long seriesId, @RequestParam("seasonId") long seasonId) throws UnauthorizedException, NotFoundException {
		seriesService.unviewSeason(seasonId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
	}

    @RequestMapping(value = "/viewEpisode", method = RequestMethod.POST)
    public ModelAndView viewEpisode(@RequestParam("seriesId") long seriesId, @RequestParam("episodeId") long episodeId,
									HttpServletRequest request) throws NotFoundException, UnauthorizedException {
		seriesService.setViewedEpisode(episodeId);
		String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:" + referer);
    }

    @RequestMapping(value = "/unviewEpisode", method = RequestMethod.POST)
    public ModelAndView unviewEpisode(@RequestParam("seriesId") long seriesId, @RequestParam("episodeId") long episodeId) throws NotFoundException, UnauthorizedException {
		seriesService.unviewEpisode(episodeId);
        return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/rate")
    public ModelAndView rate(@RequestParam("seriesId") long seriesId, @RequestParam("rating") int rating) throws NotFoundException, UnauthorizedException {
		seriesService.rateSeries(seriesId, rating);
	    return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
	public ModelAndView post(@Valid @ModelAttribute("postForm") final PostForm form, final BindingResult errors) throws Exception {
		if (errors.hasErrors()) {
			return series(form, new CommentForm(), form.getSeriesId());
		}
		seriesService.addSeriesReview(form.getBody(), form.getSeriesId());
		return new ModelAndView("redirect:/series?id=" + form.getSeriesId());
	}

	@RequestMapping(value = "/likePost", method = RequestMethod.POST)
	public ModelAndView likePost(@RequestParam("seriesId") long seriesId, @RequestParam("postId") long postId) throws NotFoundException, UnauthorizedException {
		seriesService.likePost(postId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
	}

    @RequestMapping(value = "/unlikePost", method = RequestMethod.POST)
    public ModelAndView unlikePost(@RequestParam("seriesId") long seriesId, @RequestParam("postId") long postId) throws NotFoundException, UnauthorizedException {
		seriesService.unlikePost(postId);
        return new ModelAndView("redirect:/series?id=" + seriesId);
    }

	@RequestMapping(value = "/removePost", method = RequestMethod.POST)
	public ModelAndView removePost(@RequestParam("seriesId") long seriesId, @RequestParam("postId") long postId) throws UnauthorizedException, NotFoundException {
		seriesService.removePost(postId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public ModelAndView comment(@Valid @ModelAttribute("commentForm") final CommentForm form, final BindingResult errors) throws Exception {
		if (errors.hasErrors()) {
			return series(new PostForm(), form, form.getCommentSeriesId());
		}
		seriesService.addCommentToPost(form.getCommentPostId(), form.getCommentBody());
		return new ModelAndView("redirect:/series?id=" + form.getCommentSeriesId());
	}

    @RequestMapping(value = "/likeComment", method = RequestMethod.POST)
    public ModelAndView likeComment(@RequestParam("seriesId") long seriesId, @RequestParam("postId") long postId, @RequestParam("commentId") long commentId) throws NotFoundException, UnauthorizedException {
		seriesService.likeComment(commentId);
        return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/unlikeComment", method = RequestMethod.POST)
    public ModelAndView unlikeComment(@RequestParam("seriesId") long seriesId, @RequestParam("postId") long postId, @RequestParam("commentId") long commentId) throws NotFoundException, UnauthorizedException {
		seriesService.unlikeComment(commentId);
        return new ModelAndView("redirect:/series?id=" + seriesId);
    }

	@RequestMapping(value = "/removeComment", method = RequestMethod.POST)
	public ModelAndView removeComment(@RequestParam("seriesId") long seriesId, @RequestParam("postId") long postId, @RequestParam("commentId") long commentId) throws UnauthorizedException, NotFoundException {
		seriesService.removeComment(commentId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
	}

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
	public ModelAndView users() {
		ModelAndView mav = new ModelAndView("users");
		mav.addObject("users", userService.getAllUsersExceptLoggedOne());
		return mav;
	}

    @RequestMapping(value = "/upcoming", method = RequestMethod.GET)
    public ModelAndView upcoming() throws UnauthorizedException {
        ModelAndView mav = new ModelAndView("upcoming");
        mav.addObject("upcoming", seriesService.getUpcomingEpisodes());
        return mav;
    }

    @RequestMapping(value = "/watchlist", method = RequestMethod.GET)
    public ModelAndView watchlist() throws UnauthorizedException, NotFoundException {
        ModelAndView mav = new ModelAndView("watchlist");
        mav.addObject("watchlist", seriesService.getWatchList());
        return mav;
    }

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile(@RequestParam("id") long userId,@ModelAttribute("updateUserForm") final UpdateUserForm form) throws NotFoundException, UnauthorizedException, BadRequestException {

		User u = userService.findById(userId);
		ModelAndView mav = new ModelAndView("profile");
		mav.addObject("userProfile", u);
		mav.addObject("hasAvatar", userService.getUserAvatar(userId).isPresent());
		mav.addObject("followedSeries",seriesService.getAddedSeries(userId));
		mav.addObject("recentlyWatched", seriesService.getRecentlyWatchedList(7));
		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLogin(@RequestParam(required = false) String error) {
		return new ModelAndView("login");
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
	public @ResponseBody byte[] getImageWithMediaType(@PathVariable long userId) throws IOException, NotFoundException {
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
