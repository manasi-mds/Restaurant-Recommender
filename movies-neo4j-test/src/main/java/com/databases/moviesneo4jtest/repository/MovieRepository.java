package com.databases.moviesneo4jtest.repository;

import com.databases.moviesneo4jtest.model.MovieEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends Neo4jRepository<MovieEntity, String> {
    Optional<MovieEntity> findOneByTitle(String title);
}
