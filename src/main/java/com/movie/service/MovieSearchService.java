package com.movie.service;

import com.movie.model.MovieData;

import java.util.List;

public interface MovieSearchService {
    List<MovieData> search(String keyword);
    void createAlgorithm();
}
