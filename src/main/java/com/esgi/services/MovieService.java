package com.esgi.services;

import com.esgi.model.MovieEntity;
import com.esgi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Arnaud on 03/04/2016.
 */
@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Transactional
    public MovieEntity getDetailMovie(long id) {
        return (movieRepository.findOne(id));
    }

    @Transactional
    public List<MovieEntity> getLastMovies() {
        return(movieRepository.findAll());
    }

}