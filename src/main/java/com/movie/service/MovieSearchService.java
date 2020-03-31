package com.movie.service;

import com.movie.model.MovieData;

import java.util.List;

public interface MovieSearchService {
    List<MovieData> searchByTitle(String keyword);
//    long searchById(long id);
    void createAlgorithm();
}
