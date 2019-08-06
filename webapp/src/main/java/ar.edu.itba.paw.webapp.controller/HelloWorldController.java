package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
	
	@Autowired
	private UserService us;
	
	@RequestMapping("/") //Le digo que url mappeo
	public ModelAndView helloWorld() {
		final ModelAndView mav = new ModelAndView("index"); //Seleccionar lista
		mav.addObject("greeting", "PAW"); //Popular model
		return mav;
	}
	
	@RequestMapping("/login") //Le digo que url mappeo
	public ModelAndView login() {
		final ModelAndView mav = new ModelAndView("login"); //Seleccionar lista
		mav.addObject("greeting", "PAW"); //Popular model
		return mav;
	}
	
	@RequestMapping("/logout") //Le digo que url mappeo
	public ModelAndView logout() {
		final ModelAndView mav = new ModelAndView("logout"); //Seleccionar lista
		mav.addObject("greeting", "PAW"); //Popular model
		return mav;
	}
	//No quiero repetirle todo el tiempo el path "WEB-INF/jsp/.." entonces configuro mi propio view resolver en web config
}
