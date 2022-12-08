package com.dbproject.restaurantrecommender.respsitory;

import com.dbproject.restaurantrecommender.model.UserEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<UserEntity, Long> {
}
