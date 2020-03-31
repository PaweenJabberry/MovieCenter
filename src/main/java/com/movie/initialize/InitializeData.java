package com.movie.initialize;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.model.MovieData;
import com.movie.repositories.MovieRepository;
import com.movie.service.MovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitializeData implements CommandLineRunner {

    private final MovieRepository movieRepository;

    @Autowired
    @Qualifier("invertedIndexMovieSearchService")
    private MovieSearchService movieSearchService;

    @Autowired
    private ObjectMapper objectMapper;

    private String MOVIE_DATA_URL = "https://raw.githubusercontent.com/prust/wikipedia-movie-data/master/movies.json";

    public InitializeData(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            List<MovieData> movieDataList = new ArrayList<>();
            movieDataList = objectMapper.readValue(new URL(MOVIE_DATA_URL), new TypeReference<List<MovieData>>(){});
            movieRepository.saveAll(movieDataList);
            System.out.println("Initialize Data");
            System.out.println("Data Count : "+movieRepository.count());
            movieSearchService.createAlgorithm();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
