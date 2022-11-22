package com.databases.moviesneo4jtest.services;

import com.databases.moviesneo4jtest.dto.MovieDTO;

import java.util.List;

public interface IMovieService {
    MovieDTO getMovie(String title);
    MovieDTO createMovie(MovieDTO movie);
    MovieDTO updateMovie(String movieTitle, MovieDTO movie);
    List<MovieDTO> getAllMovies();

    void deleteMovie(String title);
}
