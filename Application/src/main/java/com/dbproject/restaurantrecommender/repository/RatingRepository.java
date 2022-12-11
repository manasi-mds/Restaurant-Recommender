package com.dbproject.restaurantrecommender.repository;

import com.dbproject.restaurantrecommender.model.RatingEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends Neo4jRepository<RatingEntity, Long> {
    Optional<RatingEntity> findByRating(Double name);
}
