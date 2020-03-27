package com.movie.controller;

import com.movie.repositories.MovieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/search")
    public String searchByName() {
        return "service/list";
    }
}
