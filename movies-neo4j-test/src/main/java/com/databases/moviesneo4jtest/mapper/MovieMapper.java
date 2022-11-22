package com.databases.moviesneo4jtest.mapper;

import com.databases.moviesneo4jtest.dto.MovieDTO;
import com.databases.moviesneo4jtest.model.MovieEntity;

public class MovieMapper {

    public static MovieDTO toMovieDTO(MovieEntity movieEntity) {
        return new MovieDTO(movieEntity.getTitle(), movieEntity.getDescription());
    }
    public static MovieEntity toMovieEntity(MovieDTO movieDTO) {
        return new MovieEntity(movieDTO.getTitle(), movieDTO.getTagline());
    }
}
