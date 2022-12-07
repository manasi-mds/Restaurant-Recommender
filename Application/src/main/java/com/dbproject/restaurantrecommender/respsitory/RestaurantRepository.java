package com.dbproject.restaurantrecommender.respsitory;

import com.dbproject.restaurantrecommender.model.RestaurantEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends Neo4jRepository<RestaurantEntity, Long> {

    @Query("MATCH (n:Cuisine) where id(n)=$cuisineId return n")
    List<RestaurantEntity> havingACuisine(Long cuisineId);
}
