package com.movie.repositories;

import com.movie.model.MovieData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<MovieData,Long> {
}
