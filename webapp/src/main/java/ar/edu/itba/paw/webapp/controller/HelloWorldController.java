package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.CommentForm;
import ar.edu.itba.paw.webapp.form.PostForm;
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
import java.util.*;

@Controller
public class HelloWorldController {

	@Autowired
	private UserService userService;

	@Autowired
	private SeriesService seriesService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		User u = userService.getLoggedUser();
		if(u != null)
			System.out.println("currUser: " + u.getMailAddress());
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("newShows", seriesService.getNewestSeries(0,4));
		mav.addObject("seriesMap", seriesService.getSeriesByGenreMap(0,7));
		return mav;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam String op,@RequestParam String search) {
		//Si el parametro es vacio, lo redirecciono al home que tiene todas las series.
		if(search.length() == 0){
			return new ModelAndView("redirect:/");
		}
		final ModelAndView mav = new ModelAndView("search");
        mav.addObject("op",op);
		if(op.equals("genre")){
			Map<Genre,List<Series>> genres = seriesService.getSeriesByGenreMap(0,5);
			Map<Genre,List<Series>> searchResults = new HashMap<>();
			for(Map.Entry<Genre,List<Series>> entry : genres.entrySet()){
				if(entry.getKey().getName().toLowerCase().contains(search.toLowerCase())){
					searchResults.put(entry.getKey(),entry.getValue());
				}
			}
			mav.addObject("searchResults",searchResults);
		}
		else{
			mav.addObject("searchResults",seriesService.getSeriesByName(search));
		}
		return mav;
	}

	@RequestMapping(value = "/series", method = RequestMethod.GET)
	public ModelAndView series(@ModelAttribute("postForm") final PostForm postForm, @ModelAttribute("commentForm") final CommentForm commentForm, @RequestParam("id") long id) {
		final ModelAndView mav = new ModelAndView("series");
		User u = userService.getLoggedUser();
		mav.addObject("series", seriesService.getSerieById(id, u.getId()));
		return mav;
	}

	@RequestMapping(value = "/addSeries", method = RequestMethod.POST)
    public ModelAndView addSeries(@RequestParam("seriesId") long seriesId, @RequestParam("userId") long userId) {
//	    TODO pedro llamar a metodo de agregar serie a usuario
	    return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/viewEpisode", method = RequestMethod.POST)
    public ModelAndView viewEpisode(@RequestParam("seriesId") long seriesId, @RequestParam("episodeId") long episodeId, @RequestParam("userId") long userId) {
//	    TODO pedro llamar a metodo de ver episode
        return new ModelAndView("redirect:/series?id=" + seriesId);
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
	public ModelAndView post(@Valid @ModelAttribute("postForm") final PostForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return series(form, new CommentForm(), form.getSeriesId());
		}
//		DENTRO DE FORM HAY: form.getDescription() form.getSeriesId() form.getUserId()
//		TODO pedro llamar a metodo que postea en una serie
		return new ModelAndView("redirect:/series?id=" + form.getSeriesId());
	}

	@RequestMapping(value = "/likePost", method = RequestMethod.POST)
	public ModelAndView likePost(@RequestParam("seriesId") long seriesId, @RequestParam("userId") long userId, @RequestParam("postId") long postId) {
		// TODO pedro llamar a metodo que likea ese post
		return new ModelAndView("redirect:/series?id=" + seriesId);
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public ModelAndView comment(@Valid @ModelAttribute("commentForm") final CommentForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return series(new PostForm(), form, form.getCommentSeriesId());
		}
//		DENTRO DE COMMENT FORM HAY: getDescription() getSeriesId() getUserId() getUserId()
//		TODO pedro llamar a metodo que comenta un post de una serie
		return new ModelAndView("redirect:/series?id=" + form.getCommentSeriesId());
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile() {
		return new ModelAndView("profile");
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLogin(@RequestParam(required = false) String error) {
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegister(@ModelAttribute("registerForm") final UserForm form) {
		return new ModelAndView("register"); // para que si tengo un error en el formulario, poder tener precalculados los valores que el usuario calculo..
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return showRegister(form);
		}

		userService.createUser(form.getUsername(), form.getPassword(), form.getMail());
		// TODO create user
//		final User u = us.create(form.getUsername());
//		return new ModelAndView("redirect:/user/" + u.getId());
		return null;
	}

	@RequestMapping("/logout") //Le digo que url mappeo
	public ModelAndView logout() {
		final ModelAndView mav = new ModelAndView("logout"); //Seleccionar lista
		mav.addObject("greeting", "PAW"); //Popular model
		return mav;
	}

	// TODO sacar. Lo puse por testeo inicial del dao de user, no debería ir ésto...
//	@RequestMapping("/createuser")
//	public ModelAndView createUser(@RequestParam(value = "name", required = true) final String userName) {
//		final long id = userService.createUser(userName, "", "");
//		final ModelAndView mav = new ModelAndView("index"); //Seleccionar lista
//		mav.addObject("greeting", "PAW"); //Popular model
//		return mav;
//	}

}
