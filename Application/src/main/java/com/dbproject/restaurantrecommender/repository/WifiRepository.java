package com.dbproject.restaurantrecommender.repository;

import com.dbproject.restaurantrecommender.enums.WifiType;
import com.dbproject.restaurantrecommender.model.WifiEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WifiRepository extends Neo4jRepository<WifiEntity, Long> {
    Optional<WifiEntity> findByType(WifiType type);
}
