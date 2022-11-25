package com.dbproject.restaurantrecommender.respsitory;

import com.dbproject.restaurantrecommender.model.RestaurantEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RestaurantRepository extends Neo4jRepository<RestaurantEntity, Long> {
}
