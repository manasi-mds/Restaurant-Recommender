package com.dbproject.restaurantrecommender.repository;

import com.dbproject.restaurantrecommender.model.OutdoorSeatingEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OutdoorSeatingRepository extends Neo4jRepository<OutdoorSeatingEntity, Long> {
    Optional<OutdoorSeatingEntity> findByName(String name);
}
