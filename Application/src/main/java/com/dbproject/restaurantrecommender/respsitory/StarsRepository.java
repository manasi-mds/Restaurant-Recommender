package com.dbproject.restaurantrecommender.respsitory;


import com.dbproject.restaurantrecommender.model.StarsEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarsRepository extends Neo4jRepository<StarsEntity, Long> {
}
