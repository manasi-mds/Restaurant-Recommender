package com.databases.moviesneo4jtest.controller;

import com.databases.moviesneo4jtest.dto.MovieDTO;
import com.databases.moviesneo4jtest.services.IMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final IMovieService movieService;
    Logger logger = Logger.getLogger(MovieController.class.getName());

    @PostMapping
    MovieDTO createMovie(@RequestBody MovieDTO newMovie) {
        logger.info("Creating movie: " + newMovie.getTitle());
        return movieService.createMovie(newMovie);
    }

    @GetMapping("/{title}")
    MovieDTO getMovie(@PathVariable String title) {
        return movieService.getMovie(title);
    }

    @GetMapping
    List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }

    @PutMapping("/{title}")
    MovieDTO updateMovie(@PathVariable String title, @RequestBody MovieDTO movie) {
        return movieService.updateMovie(title, movie);
    }

    @DeleteMapping("/{title}")
    void deleteMovie(@PathVariable String title) {
        movieService.deleteMovie(title);
    }

}
