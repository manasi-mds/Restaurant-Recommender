package com.dbproject.restaurantrecommender.respsitory;

import com.dbproject.restaurantrecommender.model.RestaurantEntity;
import com.dbproject.restaurantrecommender.model.UserEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RestaurantRepository extends Neo4jRepository<RestaurantEntity, Long> {

}
