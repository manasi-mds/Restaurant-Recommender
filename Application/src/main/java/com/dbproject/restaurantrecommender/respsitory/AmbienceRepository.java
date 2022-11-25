package com.dbproject.restaurantrecommender.respsitory;

import com.dbproject.restaurantrecommender.model.AmbienceEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmbienceRepository extends Neo4jRepository<AmbienceEntity, Long> {
}

