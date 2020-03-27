package com.movie.repositories;

import com.movie.model.MovieData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<MovieData,Long> {
    List<MovieData> findByTitleContainingIgnoreCase(String queryText);
    List<MovieData> findByYear(Integer year);
}
