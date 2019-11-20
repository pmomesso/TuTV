package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AuthenticationService;
import ar.edu.itba.paw.interfaces.SeriesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewsController {

    @Autowired
	private SeriesService seriesService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, Model model, @RequestParam(value = "id", required = false) Long id, @RequestParam(value = "page", required = false) Long page) {
		final ModelAndView mav = new ModelAndView("index");

        if (id != null) {
            mav.addObject("sectionId", id);
            mav.addObject("genres", seriesService.getSeriesByGenre(id, page));
        } else {
            mav.addObject("genres", seriesService.getSeriesByGenre());
        }
		mav.addObject("newShows", seriesService.getNewestSeries(0,4));
		return mav;
	}

    @RequestMapping(value = "/genre", method = RequestMethod.GET)
    public ModelAndView homeGenre(@RequestParam("id") Long id, @RequestParam("page") String page) {
        return new ModelAndView(String.format("redirect:/?id=%d&&page=%s", id, page));
    }

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final ModelAndView mav = new ModelAndView("search");
		mav.addObject("genres",seriesService.getAllGenres());
        mav.addObject("networks",seriesService.getAllNetworks());
		return mav;
	}

	@RequestMapping(value = "/searchResults", method = RequestMethod.GET)
    public ModelAndView search(@Valid @ModelAttribute("searchForm") final SearchForm form, final BindingResult errors, @RequestParam(name = "page",required = false) Integer page) {
	    if(errors.hasErrors()){
	        return search();
        }
	    page = page == null || page < 0 ? 0 : page;
	    List<Series> results = seriesService.searchSeries(form.getName(),form.getGenre(),form.getNetwork(),page);
        ModelAndView mav;
	    if(results.size() > 0){
            mav = new ModelAndView("searchResults");
            mav.addObject("searchResults",results);
            mav.addObject("currentPage",page);
            mav.addObject("name",form.getName());
            mav.addObject("genre",form.getGenre());
            mav.addObject("network",form.getNetwork());
            if(seriesService.searchSeries(form.getName(),form.getGenre(),form.getNetwork(), page + 1).size() == 0){
                mav.addObject("last",true);
            }
        }
	    else{
            mav = search();
            mav.addObject("emptySearch",true);
            mav.addObject("form",form);
        }
        return mav;
    }

    @RequestMapping(value = "/upcoming", method = RequestMethod.GET)
    public ModelAndView upcoming() throws UnauthorizedException, NotFoundException {
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
}
