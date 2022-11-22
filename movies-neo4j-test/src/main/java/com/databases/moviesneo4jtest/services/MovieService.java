package com.databases.moviesneo4jtest.services;

import com.databases.moviesneo4jtest.dto.MovieDTO;
import com.databases.moviesneo4jtest.mapper.MovieMapper;
import com.databases.moviesneo4jtest.model.MovieEntity;
import com.databases.moviesneo4jtest.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.internal.util.Preconditions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;

    @Override
    public MovieDTO getMovie(String title) {
        return movieRepository.findOneByTitle(title)
                .map(MovieMapper::toMovieDTO)
                .orElseThrow(() -> new IllegalArgumentException("Movie with title " + title + " not found"));
    }

    @Override
    public MovieDTO createMovie(MovieDTO movie) {
        MovieEntity movieEntity = MovieMapper.toMovieEntity(movie);
        movieEntity = movieRepository.save(movieEntity);
        return MovieMapper.toMovieDTO(movieEntity);
    }

    @Override
    public MovieDTO updateMovie(String title, MovieDTO movie) {
        Preconditions.checkArgument(title.equals(movie.getTitle()), "Movie title in path must match movie title in body");
        MovieEntity movieEntity = MovieMapper.toMovieEntity(movie);
        movieEntity = movieRepository.save(movieEntity);
        return MovieMapper.toMovieDTO(movieEntity);
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream().map(MovieMapper::toMovieDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteMovie(String title) {
        movieRepository.deleteById(title);
    }
}
