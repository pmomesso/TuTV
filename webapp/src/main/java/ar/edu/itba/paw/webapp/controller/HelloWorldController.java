package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Season;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.form.CommentForm;
import ar.edu.itba.paw.webapp.form.PostForm;
import ar.edu.itba.paw.webapp.form.SearchForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
	public ModelAndView viewSeason(@RequestParam("seriesId") long seriesId, @RequestParam("seasonId") long seasonId) throws UnauthorizedException {
		seriesService.setViewedSeason(seasonId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
	}

	@RequestMapping(value = "/unviewSeason", method = RequestMethod.POST)
	public ModelAndView unviewSeason(@RequestParam("seriesId") long seriesId, @RequestParam("seasonId") long seasonId) throws UnauthorizedException {
		seriesService.unviewSeason(seasonId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
	}

    @RequestMapping(value = "/viewEpisode", method = RequestMethod.POST)
    public ModelAndView viewEpisode(@RequestParam("seriesId") long seriesId, @RequestParam("episodeId") long episodeId) throws NotFoundException, UnauthorizedException {
		seriesService.setViewedEpisode(episodeId);
        return new ModelAndView("redirect:/series?id=" + seriesId);
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
		//Todo: validate that the removal is requested by admin or by comment owner
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
		//Todo: validate that the removal is requested by admin or by comment owner
		seriesService.removeComment(commentId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
	}

    @RequestMapping(value = "/banUser", method = RequestMethod.POST)
    public ModelAndView banUser(@RequestParam("seriesId") long seriesId, @RequestParam("userId") long userId) throws UnauthorizedException, NotFoundException {
		//Todo: validate that the ban is requested by admin.
		userService.banUser(userId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/unbanUser", method = RequestMethod.POST)
    public ModelAndView unbanUser(@RequestParam("seriesId") long seriesId, @RequestParam("userId") long userId) throws UnauthorizedException, NotFoundException {
		//Todo: validate that the unban is requested by admin.
		userService.unbanUser(userId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
    }

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile(@RequestParam("id") long userId) throws NotFoundException {
		User u = userService.findById(userId);
//		TODO manejar error de si no hay user
		ModelAndView mav = new ModelAndView("profile");
		mav.addObject("userProfile", u);
		return mav;
	}

    @RequestMapping(value = "/upcoming", method = RequestMethod.GET)
    public ModelAndView upcoming() {
        ModelAndView mav = new ModelAndView("upcoming");
//        TODO add upcoming series to model
        return mav;
    }

    @RequestMapping(value = "/watchlist", method = RequestMethod.GET)
    public ModelAndView watchlist() throws UnauthorizedException {
        ModelAndView mav = new ModelAndView("watchlist");
//        TODO pedro reemplazar new ArrayList<Season>() por lo que retorne su funcion
        mav.addObject("watchlist", seriesService.getWatchList());
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
	public ModelAndView register(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return showRegister(form);
		}

		userService.createUser(form.getUsername(), form.getPassword(), form.getMail(),false);
		return new ModelAndView("redirect:/registrationsuccess");
		// TODO create user y setear admin.
	}

	@RequestMapping(value = "/uplodadAvatar", method = RequestMethod.POST)
	public ModelAndView uploadAvatar(@RequestParam("avatar") MultipartFile avatar) {
		try {
			userService.setUserAvatar(1, avatar.getBytes());
		} catch (Exception e) {
			System.out.println("ERROR EN GETBYTES SETAVATAR");
		}

		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/user/{userId}/avatar", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getImageWithMediaType(@PathVariable long userId) throws IOException {
		return userService.getUserAvatar(userId);
	}
}
