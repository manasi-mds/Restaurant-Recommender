package com.dbproject.restaurantrecommender.respsitory;

import com.dbproject.restaurantrecommender.model.AlcoholEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlcoholRepository extends Neo4jRepository<AlcoholEntity, Long> {
    Optional<AlcoholEntity> findByName(String name);
}
