package com.dbproject.restaurantrecommender.repository;

import com.dbproject.restaurantrecommender.model.AmbienceEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmbienceRepository extends Neo4jRepository<AmbienceEntity, Long> {
}

