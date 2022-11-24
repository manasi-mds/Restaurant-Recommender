package com.dbproject.restaurantrecommender.respsitory;

import com.dbproject.restaurantrecommender.model.CuisineEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuisineRepository extends Neo4jRepository<CuisineEntity, String> {
}
