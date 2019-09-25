package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Genre;
import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.webapp.form.LoginForm;
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

	@RequestMapping(value = "/serie", method = RequestMethod.GET)
	public ModelAndView serie(@RequestParam("id") long id) {
		final ModelAndView mav = new ModelAndView("serie");
		mav.addObject("serie", seriesService.getSerieById(id));
		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLogin(@ModelAttribute("loginForm") final LoginForm form) {
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@Valid @ModelAttribute("loginForm") final LoginForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return showLogin(form);
		}
		// TODO loggear usuario
		return new ModelAndView("redirect:/");
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
