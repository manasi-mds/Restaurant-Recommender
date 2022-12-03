package com.dbproject.restaurantrecommender.respsitory;

import com.dbproject.restaurantrecommender.model.UserEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends Neo4jRepository<UserEntity, Long> {

    @Query("MATCH (u:User)-[lr:LIKE_RESTAURANT]->(r:Restaurant) WHERE id(r) IN $restaurantIds RETURN u")
    Set<UserEntity> getPotentialFriends(List<Long> restaurantIds);
}
