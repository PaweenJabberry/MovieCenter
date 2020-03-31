package com.movie.controller;


import com.movie.repositories.MovieRepository;
import com.movie.service.MovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovieCenterController {

    private final MovieRepository movieRepository;

    @Autowired
    @Qualifier("invertedIndexMovieSearchService")
    private MovieSearchService movieSearchService;

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
        try {
            model.addAttribute("movies", movieRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "service/list";
    }

    //  http://localhost:8080/search?id=999
    @RequestMapping(value = "/search", params = "id")
    public String searchById(Model model, @RequestParam("id") long id) {
        try {
            model.addAttribute("movies", movieRepository.findById(id).get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "service/list";
    }

    //  http://localhost:8080/search?name=Glorious
    @RequestMapping(value = "/search", params = "name")
    public String searchByName(Model model, @RequestParam("name") String keyword) {
        try {
            long startTime = System.nanoTime();
            model.addAttribute("movies", movieRepository.findByTitleContainingIgnoreCase(keyword));
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("NormalSearch Speed : "+(duration/1000000)+" milliseconds");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "service/list";
    }

    //  http://localhost:8080/search?year=1996
    @RequestMapping(value = "/search", params = "year")
    public String searchByYear(Model model, @RequestParam("year") Integer year) {
        try {
            model.addAttribute("movies", movieRepository.findByYear(year));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "service/list";
    }

    //  http://localhost:8080/search?test=Glorious
    @RequestMapping(value = "/search", params = "test")
    public String searchByInverted(Model model, @RequestParam("test") String keyword) {
        try {
            long startTime = System.nanoTime();
            model.addAttribute("movies", movieSearchService.searchByTitle(keyword));
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("InvertedIndexSearch Speed : "+(duration/1000000)+" milliseconds");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "service/list";
    }
}
