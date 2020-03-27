package com.movie.controller;

import com.movie.repositories.MovieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MovieCenterController {

    private final MovieRepository movieRepository;

    public MovieCenterController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    //  http://localhost:8080/
    @RequestMapping("/")
    public String index() {
        return "web/index";
    }

    //  http://localhost:8080/movies
    @RequestMapping("/movies")
    public String showAllMovies(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "service/list";
    }

    //  http://localhost:8080/search?id=999
    @RequestMapping(value = "/search", params = "id")
    public String searchById(Model model, @RequestParam("id") long id) {
        model.addAttribute("movies", movieRepository.findById(id).get());
        return "service/list";
    }

    //  http://localhost:8080/search?q=Glorious
    @RequestMapping(value = "/search", params = "name")
    public String searchByName(Model model, @RequestParam("name") String keyword) {
        model.addAttribute("movies", movieRepository.findByTitleContainingIgnoreCase(keyword));
        return "service/list";
    }

    //  http://localhost:8080/search?y=1996
    @RequestMapping(value = "/search", params = "year")
    public String searchByYear(Model model, @RequestParam("year") Integer year) {
        model.addAttribute("movies", movieRepository.findByYear(year));
        return "service/list";
    }
}
