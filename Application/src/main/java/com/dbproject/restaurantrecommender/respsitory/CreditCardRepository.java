package com.dbproject.restaurantrecommender.respsitory;

import com.dbproject.restaurantrecommender.model.CreditCardEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends Neo4jRepository<CreditCardEntity, Long> {
    Optional<CreditCardEntity> findByName(String name);
}
