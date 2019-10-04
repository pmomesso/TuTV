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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
	public ModelAndView series(@ModelAttribute("postForm") final PostForm postForm, @ModelAttribute("commentForm") final CommentForm commentForm, @RequestParam("id") long id) {
		final ModelAndView mav = new ModelAndView("series");
		mav.addObject("series", seriesService.getSerieById(id));
		mav.addObject("postForm", postForm);
		mav.addObject("commentForm", commentForm);
		return mav;
	}

	@RequestMapping(value = "/addSeries", method = RequestMethod.POST)
    public ModelAndView addSeries(@RequestParam("seriesId") long seriesId) {
	    seriesService.followSeries(seriesId);
	    return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/viewEpisode", method = RequestMethod.POST)
    public ModelAndView viewEpisode(@RequestParam("seriesId") long seriesId, @RequestParam("episodeId") long episodeId) {
		seriesService.setViewedEpisode(episodeId);
        return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/unviewEpisode", method = RequestMethod.POST)
    public ModelAndView unviewEpisode(@RequestParam("seriesId") long seriesId, @RequestParam("episodeId") long episodeId) {
		seriesService.unviewEpisode(episodeId);
        return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/rate")
    public ModelAndView rate(@RequestParam("seriesId") long seriesId, @RequestParam("rating") int rating) {
		seriesService.rateSeries(seriesId, rating);
	    return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
	public ModelAndView post(@Valid @ModelAttribute("postForm") final PostForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return series(form, new CommentForm(), form.getSeriesId());
		}
		//TODO pedro que el método reciba un form más que los campos de form...
		seriesService.addSeriesReview(form.getBody(), form.getSeriesId());
		return new ModelAndView("redirect:/series?id=" + form.getSeriesId());
	}

	@RequestMapping(value = "/likePost", method = RequestMethod.POST)
	public ModelAndView likePost(@RequestParam("seriesId") long seriesId, @RequestParam("postId") long postId) {
		seriesService.likePost(postId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
	}

    @RequestMapping(value = "/unlikePost", method = RequestMethod.POST)
    public ModelAndView unlikePost(@RequestParam("seriesId") long seriesId, @RequestParam("postId") long postId) {
		seriesService.unlikePost(postId);
        return new ModelAndView("redirect:/series?id=" + seriesId);
    }

	@RequestMapping(value = "/removePost", method = RequestMethod.POST)
	public ModelAndView removePost(@RequestParam("seriesId") long seriesId, @RequestParam("postId") long postId) throws UnauthorizedException {
		seriesService.removePost(postId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public ModelAndView comment(@Valid @ModelAttribute("commentForm") final CommentForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return series(new PostForm(), form, form.getCommentSeriesId());
		}
		//TODO que este metodo tome el form y no los parámetros directamente... (sería más correcto??)
		//Todo: validate that this is requested by the same userc
		seriesService.addCommentToPost(form.getCommentPostId(), form.getCommentBody());
		return new ModelAndView("redirect:/series?id=" + form.getCommentSeriesId());
	}

    @RequestMapping(value = "/likeComment", method = RequestMethod.POST)
    public ModelAndView likeComment(@RequestParam("seriesId") long seriesId, @RequestParam("postId") long postId, @RequestParam("commentId") long commentId) {
		//Todo: validate that the like is requested by the same user
		seriesService.likeComment(commentId);
        return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/unlikeComment", method = RequestMethod.POST)
    public ModelAndView unlikeComment(@RequestParam("seriesId") long seriesId, @RequestParam("postId") long postId, @RequestParam("commentId") long commentId) {
		//Todo: validate that the unlike is requested by the same user
		seriesService.unlikeComment(commentId);
        return new ModelAndView("redirect:/series?id=" + seriesId);
    }

	@RequestMapping(value = "/removeComment", method = RequestMethod.POST)
	public ModelAndView removeComment(@RequestParam("seriesId") long seriesId, @RequestParam("postId") long postId, @RequestParam("commentId") long commentId) throws UnauthorizedException {
		//Todo: validate that the removal is requested by admin
		seriesService.removeComment(commentId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
	}

    @RequestMapping(value = "/banUser", method = RequestMethod.POST)
    public ModelAndView banUser(@RequestParam("seriesId") long seriesId, @RequestParam("userId") long userId) throws UnauthorizedException {
		//Todo: validate that the ban is requested by admin.
		userService.banUser(userId);
		return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/unbanUser", method = RequestMethod.POST)
    public ModelAndView unbanUser(@RequestParam("seriesId") long seriesId, @RequestParam("userId") long userId) throws UnauthorizedException {
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
    public ModelAndView watchlist() {
        ModelAndView mav = new ModelAndView("watchlist");
//        TODO pedro reemplazar new ArrayList<Season>() por lo que retorne su funcion
        mav.addObject("watchlist", new ArrayList<Season>());
        return mav;
    }

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLogin(@RequestParam(required = false) String error) {
		return new ModelAndView("login");
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
		// TODO create user y setear admin.
//		final User u = us.create(form.getUsername());
//		return new ModelAndView("redirect:/user/" + u.getId());
		return null;
	}
}
